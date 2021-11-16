package mapmovement;

import board.OptimizedBoard;
import board.moves.Move;
import game.GameBoard;

import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MovementMap
{
    // map of all possible replies for the current move(object)
    private final Map<Move, MovementMap> movementMap;

    //a queue from which the threads should take the moves
    private static Queue<MovementMap> movementMapQueue = new SynchronousQueue<>();

    private static MovementMap currentMoveFromTheGame;

    //the move before
    private MovementMap parent = null;

    //the current move
    private Move currentMove = null;

    //score including the best response
    private AtomicInteger score;

    private AtomicBoolean isMovePossibleForCurrentGame = new AtomicBoolean(true);

    private Move currentBestResponse = null;

    public MovementMap(MovementMap parent, Move currentMove)
    {
        this(parent,currentMove,new ConcurrentHashMap<>());
    }

    public MovementMap(MovementMap parent, Move currentMove, Map<Move,MovementMap> movementMap)
    {
        this.parent = parent;
        this.currentMove = currentMove;
        this.movementMap = movementMap;
    }

    //to reduce memory compute the moves since it will only take a few moves
    public synchronized OptimizedBoard getBoardForCurrentPosition() throws CloneNotSupportedException
    {
        Stack<Move> moveStack = new Stack<>();
        MovementMap movementMap = this;
        while(movementMap!=null && !movementMap.currentMove.equals(MovementMap.currentMoveFromTheGame.currentMove))
        {
            moveStack.add(currentMove);
            movementMap = parent;

        }
        OptimizedBoard optimizedBoard = (OptimizedBoard) GameBoard.currentBoard.clone();
        while(!moveStack.isEmpty())
        {
            optimizedBoard.move(moveStack.pop());
        }
        return optimizedBoard;
    }

    //update sent from the child to the parent that we found a new best response
    protected synchronized void updateScore(Move move)
    {
        //TODO: check if it should be maximum or minimum
        if (score.get() < currentMove.getScore() - move.moveScore()) {

            currentMove.setBestResponse(move);
            score.set(currentMove.getScore() - move.moveScore());
            if (parent != null) {
                parent.updateScore(currentMove);
            }
        }
        //update all parents until root
    }

    //each thread should call this method before analyzing a move
    public synchronized boolean isCurrentMovePossible()
    {
        AtomicBoolean isCurrentMovePossible = new AtomicBoolean(true);
        if (!isMovePossibleForCurrentGame.get()) {
            isCurrentMovePossible.set(false);
        }
        else if (parent == null) {
            isCurrentMovePossible.set(false);
        }
        else {
            isCurrentMovePossible.set(parent.isCurrentMovePossible());
        }
        this.isMovePossibleForCurrentGame.set(isCurrentMovePossible.get());

        return isMovePossibleForCurrentGame.get();
    }

    //make a current move in the game which updates the whole map
    protected synchronized static void makeMovement(Move chosenMove)
    {
        Map<Move,MovementMap> movementMap = currentMoveFromTheGame.getMovementMap();
        for (Move move : movementMap.keySet()) {
            //all other moves except the one that is made become impossible to reach
            if (!chosenMove.equals(move)) {
                //make move impossible
                movementMap.get(move).isMovePossibleForCurrentGame.set(false);
            }
        }
        currentMoveFromTheGame = movementMap.get(chosenMove);
    }

    public Map<Move, MovementMap> getMovementMap()
    {
        return movementMap;
    }
}

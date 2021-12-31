package mapmovement;

import board.Board;
import board.moves.Move;
import game.GameBoard;
import game.SingleThreadCalculator;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MovementMap
{
    // map of all possible replies for the current move(object)
    private final Map<Move, MovementMap> movementMap;

    //a queue from which the threads should take the moves
    public static Queue<MovementMap> movementMapQueue = new LinkedList<MovementMap>();

    public static MovementMap currentMoveFromTheGame;

    //the move before
    private MovementMap parent = null;

    //the current move
    private Move currentMove = null;

    //score including the best response
    private AtomicInteger score;

    private AtomicBoolean isMovePossibleForCurrentGame = new AtomicBoolean(true);

    //TODO: Verify if the constructor could be made private
    public MovementMap(MovementMap parent, Move currentMove)
    {
        this(parent, currentMove, new ConcurrentHashMap<>());
    }

    public MovementMap(MovementMap parent, Move currentMove, Map<Move, MovementMap> movementMap)
    {
        this.parent = parent;
        this.currentMove = currentMove;
        this.movementMap = movementMap;
        score = new AtomicInteger(currentMove.moveScore());

    }

    //to reduce memory compute the moves since it will only take a few moves
    public synchronized Board getBoardForCurrentPosition() throws CloneNotSupportedException
    {
        Stack<Move> moveStack   = new Stack<>();
        MovementMap movementMap = this;
        while (movementMap != null && !movementMap.currentMove.equals(MovementMap.currentMoveFromTheGame.currentMove)) {
            moveStack.add(movementMap.currentMove);
            movementMap = movementMap.getParent();

        }
       // System.out.println(moveStack);
      //  System.out.println(SingleThreadCalculator.movesLowerThanDepth);
      //  System.out.println(MovementMap.movementMapQueue.size());
        Board board = (Board) GameBoard.actualBoard.clone();
        while (!moveStack.isEmpty()) {
            board.move(moveStack.pop());
            board.nextTurn();
        }
        return board;
    }

    public synchronized void addResponse(Move move) throws InterruptedException
    {
        //remove this row if something goes bad
        move.moveScore();
        MovementMap newMovementMap = new MovementMap(this, move);
        movementMap.put(move, newMovementMap);
        movementMapQueue.add(newMovementMap);

        updateScore(move);
    }

    //update sent from the child to the parent that we found a new best response
    protected synchronized void updateScore(Move move)
    {
        //TODO: check if it should be maximum or minimum
        if (score.get() >= currentMove.getScore() - move.moveScore() || currentMove.getBestResponse() == null) {

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
            isCurrentMovePossible.set(true);
        }
        else {
            isCurrentMovePossible.set(parent.isCurrentMovePossible());
        }
        this.isMovePossibleForCurrentGame.set(isCurrentMovePossible.get());

        return isMovePossibleForCurrentGame.get();
    }

    //make a current move in the game which updates the whole map
    public synchronized static void makeMovement(Move chosenMove)
    {
        Map<Move, MovementMap> movementMap = currentMoveFromTheGame.getMovementMap();
        for (Move move : movementMap.keySet()) {
            //all other moves except the one that is made become impossible to reach
            if (!chosenMove.equals(move)) {
                //make move impossible
                movementMap.get(move).isMovePossibleForCurrentGame.set(false);
            }
        }
        currentMoveFromTheGame = movementMap.get(chosenMove);
    }

    public synchronized int getCurrentDepth()
    {
        MovementMap movementMap = this;
        int         depthCount  = 0;

        while (movementMap != null && !movementMap.currentMove.equals(MovementMap.currentMoveFromTheGame.currentMove)) {
            depthCount++;
            movementMap = movementMap.getParent();

        }
        return depthCount;
    }

    public Move getCurrentMove()
    {
        return currentMove;
    }

    public void setCurrentMove(Move currentMove)
    {
        this.currentMove = currentMove;
    }

    public MovementMap getParent()
    {
        return parent;
    }

    public Map<Move, MovementMap> getMovementMap()
    {
        return movementMap;
    }

    @Override
    public String toString()
    {
        return "MovementMap{" +
                "currentMove=" + currentMove +
                ", score=" + score +
                ", isMovePossibleForCurrentGame=" + isMovePossibleForCurrentGame +
                ", parent=" + parent +
                '}';
    }
}

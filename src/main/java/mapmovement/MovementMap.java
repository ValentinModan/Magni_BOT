package mapmovement;

import board.Board;
import board.moves.Move;
import game.GameBoard;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MovementMap
{
    // map of all possible replies for the current move(object)
    private Map<Move, MovementMap> movementMap;

    //a queue from which the threads should take the moves
    public static Queue<MovementMap> movementMapQueue = new LinkedList<MovementMap>();

    public static MovementMap currentMoveFromTheGame;

    //the move before
    private final MovementMap parent;

    //the current move
    private Move currentMove;

    //score including the best response
    private final AtomicInteger score;

    private final AtomicBoolean isMovePossibleForCurrentGame = new AtomicBoolean(true);

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
        Stack<Move> moveStack = new Stack<>();
        MovementMap movementMap = this;
        while (movementMap != null && !movementMap.currentMove.equals(MovementMap.currentMoveFromTheGame.currentMove)) {
            moveStack.add(movementMap.currentMove);
            movementMap = movementMap.getParent();

        }

        //TODO do not use the actual board from the game
        Board board = (Board) GameBoard.actualBoard.clone();
        while (!moveStack.isEmpty()) {
            board.move(moveStack.pop());
            board.nextTurn();
        }
        return board;
    }

    public synchronized void addResponse(Move move)
    {
        MovementMap newMovementMap = new MovementMap(this, move);
        movementMap.put(move, newMovementMap);
        movementMapQueue.add(newMovementMap);

        updateScore(move);
    }

    //update sent from the child to the parent that we might have found a new best response
    public synchronized void updateScore(Move move)
    {
        if(move.isCheckMate())
        {
            currentMove.setBestResponse(move);
            if (parent != null) {
                parent.updateScore(currentMove);
            }
            return;
        }
        int moveScore = move.moveScore();

        if (currentMove.moveScore() > currentMove.getScore() - moveScore || currentMove.getBestResponse() == null) {
            currentMove.setBestResponse(move);

        }
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
            boolean isCurrentMovePossible1 = parent.isCurrentMovePossible();
            isCurrentMovePossible.set(isCurrentMovePossible1);
            if (!isCurrentMovePossible1) {
                movementMap = null;
            }
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
        int depthCount = 0;


        if (currentMove == null) {
            log.error("Something is really wrong here" + this.toString());
            return 100;
        }
        while (movementMap.currentMove != null && !movementMap.currentMove.equals(MovementMap.currentMoveFromTheGame.currentMove)) {
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

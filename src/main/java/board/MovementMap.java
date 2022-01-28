package board;

import board.moves.Move;
import game.GameBoard;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MovementMap
{
    // map of all possible replies for the current move(object)
    private Map<Move, MovementMap> movementMap;

    public static MovementMap currentMoveFromTheGame;

    //the move before
    private final MovementMap parent;

    public int score;
    public int final_score;

    //the current move
    private Move currentMove;

    private boolean isMovePossibleForCurrentGame = true;

    public MovementMap(MovementMap parent, Move currentMove)
    {
        this(parent, currentMove, new HashMap<>());
    }

    private MovementMap(MovementMap parent, Move currentMove, Map<Move, MovementMap> movementMap)
    {
        this.parent = parent;
        this.currentMove = currentMove;
        this.movementMap = movementMap;
    }

    //to reduce memory, compute the moves since it will only take a few moves
    public Board generateBoardForCurrentPosition() throws CloneNotSupportedException
    {
        Stack<Move> moveStack = getMovesStack();

        Board board = (Board) GameBoard.actualBoard.clone();

        while (!moveStack.isEmpty()) {
            Move move = moveStack.pop();
            board.move(move);
            board.nextTurn();
        }
        return board;
    }

    Stack<Move> getMovesStack()
    {
        Stack<Move> moveStack = new Stack<>();
        MovementMap movementMap = this;
        while (movementMap != null && movementMap != MovementMap.currentMoveFromTheGame) {
            moveStack.add(movementMap.currentMove);
            movementMap = movementMap.getParent();
        }

        return moveStack;
    }

    //each thread should call this method before analyzing a move
    public synchronized boolean isCurrentMovePossible()
    {
        return isMovePossibleForCurrentGame;
    }

    public void markMovesAsImpossible()
    {
        isMovePossibleForCurrentGame = false;
        if (movementMap == null) {
            return;
        }
        for (MovementMap movementMap : movementMap.values()) {
            if (movementMap.isMovePossibleForCurrentGame) {
                movementMap.markMovesAsImpossible();
            }
        }
        //todo: this causes the error
        movementMap = null;
    }

    //make a current move in the game which updates the whole map
    public static void makeMovement(Move chosenMove)
    {
        Map<Move, MovementMap> movementMap = currentMoveFromTheGame.getMovementMap();
        for (Move move : movementMap.keySet()) {
            //all other moves except the one that is made become impossible to reach
            if (!chosenMove.equals(move)) {
                //make move impossible
                movementMap.get(move).markMovesAsImpossible();
            }
        }
        currentMoveFromTheGame = movementMap.get(chosenMove);
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
        return ""+final_score + " " + score +  "MovementMap{" +
                "currentMove=" + currentMove +
                ", isMovePossibleForCurrentGame=" + isMovePossibleForCurrentGame +
                ", parent=" + parent +
                '}';
    }
}

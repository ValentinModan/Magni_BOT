package game;

import board.Board;
import board.moves.Move;
import mapmovement.MovementMap;

import java.util.List;

public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;

    public static int movesLowerThanDepth = 0;

    public Move bestResponse(Board board) throws InterruptedException, CloneNotSupportedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);
        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }
        movesLowerThanDepth = 200000;
        computeAllDepth();

        Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        System.out.println("Best Move chosen");
        Move display = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        while(display!=null) {

            System.out.println(display);
            display = display.getBestResponse();
        }

        MovementMap.makeMovement(bestResponse);

        if (board.allMoves.size() % 5 == 0) {
            System.gc();
            System.out.println("GC done");
        }
        return bestResponse;
    }

    public void computeAllDepth() throws InterruptedException, CloneNotSupportedException
    {
        while (movesLowerThanDepth > 0) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();

            if (movementMap.isCurrentMovePossible() || movementMap.getParent() == null) {
                if (movementMap.getCurrentDepth() > GameBoard.depth) {
                    MovementMap.movementMapQueue.add(movementMap);
                }
                else {
                    Board board = movementMap.getBoardForCurrentPosition();
                    board.computePossibleMoves();
                    List<Move> possibleMovesCalculatorsList = board.getPossibleMoves();
                    for (Move move : possibleMovesCalculatorsList) {
                        movementMap.addResponse(move);
                    }
                    movesLowerThanDepth--;
                }
            }

        }
    }

    public void setup(Board board, int depth) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, board.lastMove());
        MovementMap.currentMoveFromTheGame = movementMap;

        MovementMap.movementMapQueue.add(movementMap);
        movesLowerThanDepth++;

    }
}

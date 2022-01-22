package game;

import board.Board;
import board.moves.Move;
import board.pieces.PieceType;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.extern.slf4j.Slf4j;
import mapmovement.MovementMap;

import java.util.List;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;

    public int movesLowerThanDepth = 10000;

    public Move bestResponse(Board board) throws InterruptedException, CloneNotSupportedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);
        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }
        movesLowerThanDepth = 240000;
        computeAllDepth();

        //Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        Move bestResponse = getBestResponseCalculated(MovementMap.currentMoveFromTheGame);

        MovementMap.makeMovement(bestResponse);

        if (board.allMoves.size() % 5 == 0) {
            System.gc();
            System.out.println("GC done");
        }
        clearImpossibleMovesFromQueue();
        return bestResponse;
    }

    private void clearImpossibleMovesFromQueue()
    {
        int n = MovementMap.movementMapQueue.size();

        for (int i = 1; i < n; i++) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();
            if ((movementMap.isCurrentMovePossible() || movementMap.getParent() == null)) {
                MovementMap.movementMapQueue.add(movementMap);
            }
        }
    }

    private Move getBestResponseCalculated(MovementMap movementMap)
    {
        //verify if it is a checkmate
        for (Move move : movementMap.getMovementMap().keySet()) {
            if (move.isCheckMate()) {
                return move;
            }
        }
        Move bestResponse = null;
        int best_value = -999999;

        for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
            int new_value = getMovementMapScore(movementMap1, 20);
            if (new_value > best_value) {
                System.out.println("new best value is " + new_value + " with new move " + bestResponse);
                best_value = new_value;
                bestResponse = movementMap1.getCurrentMove();
            }
        }
        return bestResponse;
    }

    public static int getMovementMapScore(MovementMap movementMap, int depth)
    {
        if (movementMap.getCurrentMove().isCheckMate()) {
            return GameOptions.CHECK_MATE_SCORE * depth;
        }

        int score = GameOptions.getSingleMoveScore(movementMap.getCurrentMove());

        //highest response score
        if(movementMap.getMovementMap()==null)
        {
            return score;
        }
        int responseScore = 0;
        if (movementMap.getMovementMap().values().size() != 0) {
            responseScore = -999999;
        }

        for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
            int value = getMovementMapScore(movementMap1, depth - 1);
            if (value > responseScore) {
                responseScore = value;
            }
        }
        return score - responseScore;
    }


    public void computeAllDepth() throws CloneNotSupportedException
    {
        while (movesLowerThanDepth > 0 && !MovementMap.movementMapQueue.isEmpty()) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();

            if (!movementMap.isCurrentMovePossible()) {
                continue;
            }
            if (movementMap.getCurrentDepth() > GameBoard.depth) {
                MovementMap.movementMapQueue.add(movementMap);
                continue;
            }
            movesLowerThanDepth--;

            Board board = movementMap.generateBoardForCurrentPosition();
            List<Move> possibleMovesCalculatorsList = board.calculatePossibleMoves();
            //is a checkmate move
            if (possibleMovesCalculatorsList.isEmpty()) {
                if(KingSafety.isTheKingAttacked(board)) {
                    movementMap.getCurrentMove().setCheckMate(true);
                    if(movementMap.getParent()!=null)
                    {
                       // movementMap.getParent().foundCheckMate(movementMap.getCurrentMove());
                    }
                }
                else
                {
                    movementMap.getCurrentMove().setStaleMate(true);
                }
                continue;
            }
            for (Move move : possibleMovesCalculatorsList) {
                movementMap.addResponse(move);
            }

        }

    }

    public void setup(Board board, int depth) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, new Move(board.getKingPosition(), board.getKingPosition()));
        MovementMap.currentMoveFromTheGame = movementMap;
        movementMap.clearObjects();

        MovementMap.movementMapQueue.add(movementMap);
        //toremove
        movesLowerThanDepth++;

    }
}

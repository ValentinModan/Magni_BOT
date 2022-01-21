package game;

import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.pieces.PieceType;
import game.gameSetupOptions.GameOptions;
import lombok.extern.slf4j.Slf4j;
import mapmovement.MovementMap;

import java.util.List;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;

    public static int movesLowerThanDepth = 10000;


    public Move bestResponse(Board board) throws InterruptedException, CloneNotSupportedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);

        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }
        movesLowerThanDepth = 20000;
        computeAllDepth();

        //Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        Move bestResponse = getBestResponseCalculated(MovementMap.currentMoveFromTheGame);
        log.info("Best move chosen is " + bestResponse);
        Move display = bestResponse;

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

    private Move getBestResponse(MovementMap movementMap)
    {
        if (movementMap == null || movementMap.getCurrentMove() == null) {
            return null;
        }
        Move bestMove = movementMap.getCurrentMove().getBestResponse();

        for (Move move : movementMap.getMovementMap().keySet()) {
            if (bestMove.moveScore() > movementMap.getMovementMap().get(move).getCurrentMove().moveScore()) {
                bestMove = move;
            }
        }
        return bestMove;
    }

    private Move getBestResponseCalculated(MovementMap movementMap)
    {
        //verify if it is a checkmate
        for (Move move : movementMap.getMovementMap().keySet()) {
            if (move.isCheckMate()) {
                return move;
            }
        }

        Move bestResponse = movementMap.getCurrentMove().getBestResponse();
        int best_value = GameOptions.getSingleMoveScore(bestResponse);

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
        while (movesLowerThanDepth > 0 && MovementMap.movementMapQueue.size() > 0) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();

            if (breakCondition(movementMap)) {
                movesLowerThanDepth++;
            }

            if (movementMap.isCurrentMovePossible() || movementMap.getParent() == null) {
                if (movementMap.getCurrentDepth() > GameBoard.depth) {
                    MovementMap.movementMapQueue.add(movementMap);
                }
                else {
                    Board board = movementMap.getBoardForCurrentPosition();
                    //to remove this
                    board.computePossibleMoves();
                    List<Move> possibleMovesCalculatorsList = board.getPossibleMoves();

                    //is a checkmate move
                    if (possibleMovesCalculatorsList.isEmpty()) {
                        movementMap.getCurrentMove().setCheckMate(true);
                        movementMap.getParent().updateScore(movementMap.getCurrentMove());
                    }
                    for (Move move : possibleMovesCalculatorsList) {
                        if (movementMap.getCurrentMove().getTakenPiece() == null || (movementMap.getCurrentMove().getTakenPiece() != null &&
                                movementMap.getCurrentMove().getTakenPiece().getPieceType() != PieceType.KING)) {
                            movementMap.addResponse(move);
                        }

                    }
                    movesLowerThanDepth--;
                }
            }

        }
    }

    boolean breakCondition(MovementMap movementMap)
    {
        if (movementMap.getParent() != null &&
                movementMap.getParent().getCurrentMove().equals(MoveConvertor.stringToMove("h8e8"))) {
            return true;
        }
        return false;
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

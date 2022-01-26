package game;

import board.Board;
import board.Position;
import board.moves.Move;
import board.pieces.PieceType;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.extern.slf4j.Slf4j;
import mapmovement.MovementMap;

import java.util.List;
import java.util.NoSuchElementException;

import static mapmovement.MovementMap.movementMapQueue;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;
    private static final int ZERO = 0;

    public static final int movesToCalculate = 800000;
    public int movesLowerThanDepth = 10000;

    public Move bestResponse(Board board) throws InterruptedException, CloneNotSupportedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board);
        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }
        clearImpossibleMovesFromQueue();

        computeAllDepth();

        //Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        Move bestResponse = getBestResponseCalculated(MovementMap.currentMoveFromTheGame, board);

        MovementMap.makeMovement(bestResponse);

        clearImpossibleMovesFromQueue();

        return bestResponse;
    }

    private void clearImpossibleMovesFromQueue()
    {
        int n = movementMapQueue.size();

        for (int i = 1; i <= n; i++) {
            MovementMap movementMap = movementMapQueue.remove();
            if ((movementMap.isCurrentMovePossible() || movementMap.getParent() == null)) {
                movementMapQueue.add(movementMap);
            }
        }
    }

    public Move getBestResponseCalculated(MovementMap movementMap, Board board)
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

            Move move = movementMap1.getCurrentMove();
            try {
                if (move.getMovingPiece().getPieceType() == PieceType.PAWN) {
                    Position leftDiagonalPosition = move.getFinalPosition().leftDiagonal(move.getMovingPiece().isWhite());
                    Position rightDiagonalPosition = move.getFinalPosition().rightDiagonal(move.getMovingPiece().isWhite());

                    if (board.getMovingPiece(leftDiagonalPosition) != null) {
                        new_value++;
                    }

                    if (board.getMovingPiece(rightDiagonalPosition) != null) {
                        new_value++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        if (movementMap.getCurrentMove().isStaleMate()) {
            return GameOptions.STALE_MATE_SCORE * depth;
        }

        int moveScore = GameOptions.getSingleMoveScore(movementMap.getCurrentMove());

        int maxResponseScore = movementMap.getMovementMap().values()
                .stream().map(it -> getMovementMapScore(it, depth - 1))
                .mapToInt(it -> it)
                .max().orElse(ZERO);

        return moveScore - maxResponseScore;
    }

    public void computeAllDepth() throws CloneNotSupportedException
    {
        movesLowerThanDepth = movesToCalculate;
        while (movesLowerThanDepth > 0 && !movementMapQueue.isEmpty()) {
            MovementMap movementMap = movementMapQueue.remove();
            movesLowerThanDepth--;
            if (!movementMap.isCurrentMovePossible()) {
                continue;
            }
            if (movementMap.getParent() != null) {

                if (!movementMap.getParent().isCurrentMovePossible()) {
                    movementMap.getParent().markMovesAsImpossible();
                    continue;
                }

                if(movementMap.getParent().getParent()!=null)
                {
                    if(!movementMap.getParent().getParent().isCurrentMovePossible())
                    {
                        movementMap.getParent().getParent().markMovesAsImpossible();
                        continue;
                    }
                    if(movementMap.getParent().getParent().getParent()!=null)
                    {
                        if(!movementMap.getParent().getParent().getParent().isCurrentMovePossible())
                        {
                            movementMap.getParent().getParent().getParent().markMovesAsImpossible();
                            continue;
                        }
                    }
                }

            }


            if (movementMap.getCurrentDepth() > GameBoard.depth) {
                movementMapQueue.add(movementMap);
                continue;
            }

            if (movesLowerThanDepth % 1000 == 0) {
                System.out.println(movesLowerThanDepth);
            }
            computeMove(movementMap);
        }
    }

    public void computeMove(MovementMap movementMap) throws CloneNotSupportedException
    {
        Board board = movementMap.generateBoardForCurrentPosition();


        List<Move> possibleMovesCalculatorsList = board.calculatePossibleMoves();

        //is a checkmate move
        if (possibleMovesCalculatorsList.isEmpty()) {
            if (KingSafety.isTheKingAttacked(board)) {
                movementMap.getCurrentMove().setCheckMate(true);
            }
            else {
                movementMap.getCurrentMove().setStaleMate(true);
            }
        }
        else {
            for (Move move : possibleMovesCalculatorsList) {

                MovementMap newMovementMap = new MovementMap(movementMap, move);
                movementMap.getMovementMap().put(move, newMovementMap);
                movementMapQueue.add(newMovementMap);
            }
        }
    }

    public void setup(Board board) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, new Move(board.getKingPosition(), board.getKingPosition()));
        MovementMap.currentMoveFromTheGame = movementMap;
        movementMap.clearObjects();

        movementMapQueue.add(movementMap);
        //toremove
        movesLowerThanDepth++;

    }
}

package game;

import board.Board;
import board.Position;
import board.moves.Move;
import board.pieces.PieceType;
import board.setup.BoardSetup;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mapmovement.MovementMap;

import java.util.*;
import java.util.stream.Collectors;

import static mapmovement.MovementMap.movementMapQueue;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;
    private static final int ZERO = 0;

    public static final int movesToCalculate = 3000000;
    public int movesLowerThanDepth;

    boolean hasDoubledDepth = false;

    boolean hasTripledDepth = false;

    Map<String, Integer> previousMovesMap = new HashMap<>();

    public void updateFenMap(Board board) throws Exception
    {
        String key = BoardSetup.boardToFenNotation(board);
        Integer value = previousMovesMap.get(key);
        previousMovesMap.put(key, value != null ? value + 1 : 1);
    }

    @SneakyThrows
    public Move bestResponse(Board board)
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board);
            computeTree();
        }
        else {
            MovementMap.makeMovement(board.lastMove());
            updateFenMap(board);

        }
        clearImpossibleMovesFromQueue();

        //computeAllDepth();
        computeTree();
        if (!hasDoubledDepth && board.getMovingPiecesMap().size() + board.getTakenPiecesMap().size() <= 7) {
            hasDoubledDepth = true;
            computeTree();
        }


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

    public Move getBestResponseCalculated(MovementMap movementMap, Board board) throws Exception
    {
        //verify if it is a checkmate
        for (Move move : movementMap.getMovementMap().keySet()) {
            if (move.isCheckMate()) {
                return move;
            }
        }
        Move bestResponse = null;
        int best_value = -999999;

        for (MovementMap movementMap1 : movementMap.getMovementMap().values().stream().sorted(
                Comparator.comparingInt(o -> o.getCurrentMove().getMovingPiece().getScore())).collect(Collectors.toList())) {
            int new_value = getMovementMapScore(movementMap1, 20);

            Move move = movementMap1.getCurrentMove();
            if (move.isPawnPromotion()) {
                new_value += 10;
            }
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

            Board board1 = movementMap1.generateBoardForCurrentPosition();
            String fenString = BoardSetup.boardToFenNotation(board1);
            Integer positionOccurance = previousMovesMap.get(BoardSetup.boardToFenNotation(board1));
            if (positionOccurance != null && positionOccurance >= 1) {
                int scoreDiference = BoardSetup.boardScoreDifference(board1);
                log.info("Position occured before\n" + fenString + "\n with score " + scoreDiference);
                log.info("" + Board.myColorWhite);
                if (scoreDiference >= 3) {
                    new_value -= 30;
                }
                if (scoreDiference <= -2) {
                    new_value += 30;
                }
            }


            if (new_value > best_value) {
                System.out.println("new best value is " + new_value + " with new move " + movementMap1.getCurrentMove());
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

    public void computeTree() throws CloneNotSupportedException
    {
        //  movesLowerThanDepth = movesToCalculate;
        // while (movesLowerThanDepth > 0) {
        computeMap(MovementMap.currentMoveFromTheGame);
        //  }
    }

    public void computeMap(MovementMap movementMap) throws CloneNotSupportedException
    {
        if (movementMap.getMovementMap().isEmpty()) {
            computeMove(movementMap, 2);
        }
        else {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                computeMap(movementMap1);
            }
        }
    }


    public void computeAllDepth() throws CloneNotSupportedException
    {
        movesLowerThanDepth = movesToCalculate;
        while (movesLowerThanDepth > 0 && !movementMapQueue.isEmpty()) {


            //to not use too much memory
            if (movesLowerThanDepth > 100000 && movementMapQueue.size() > 1000000) {
                movesLowerThanDepth = 100000;
                if (GameBoard.depth > 3) {
                    GameBoard.depth--;
                }
            }

            MovementMap movementMap = movementMapQueue.remove();
            movesLowerThanDepth--;
            if (isMovementMapValidForTheGame(movementMap)) {
                continue;
            }


            if (movementMap.getCurrentDepth() > GameBoard.depth) {
                movementMapQueue.add(movementMap);
                continue;
            }

            if (movesLowerThanDepth % 1000 == 0) {
                System.out.println(movesLowerThanDepth);
            }
            computeMove(movementMap, 1);
        }
    }

    private boolean isMovementMapValidForTheGame(MovementMap movementMap)
    {
        if (!movementMap.isCurrentMovePossible()) {
            return false;
        }
        if (movementMap.getParent() != null) {

            if (!movementMap.getParent().isCurrentMovePossible()) {
                movementMap.getParent().markMovesAsImpossible();
                return false;
            }

            if (movementMap.getParent().getParent() != null) {
                if (!movementMap.getParent().getParent().isCurrentMovePossible()) {
                    movementMap.getParent().getParent().markMovesAsImpossible();
                    return false;
                }
                if (movementMap.getParent().getParent().getParent() != null) {
                    if (!movementMap.getParent().getParent().getParent().isCurrentMovePossible()) {
                        movementMap.getParent().getParent().getParent().markMovesAsImpossible();
                        return false;
                    }
                    if (movementMap.getParent().getParent().getParent().getParent() != null) {
                        if (!movementMap.getParent().getParent().getParent().getParent().isCurrentMovePossible()) {
                            movementMap.getParent().getParent().getParent().getParent().markMovesAsImpossible();
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }

    public void computeMove(MovementMap movementMap, int n) throws CloneNotSupportedException
    {
        if (n <= 0) {
            return;
        }
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

                computeMove(newMovementMap, n - 1);
                movesLowerThanDepth--;
                // movementMapQueue.add(newMovementMap);
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

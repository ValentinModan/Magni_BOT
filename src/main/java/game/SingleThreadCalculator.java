package game;

import board.Board;
import board.MovementMap;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.MoveChecker;
import board.setup.BoardSetup;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static board.pieces.PieceType.PAWN;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;
    private static final int ZERO = 0;
    private static final int NUMBER_OF_MOVES = 20000;
    public static int number_of_computes = 20000;
    public static boolean numberStrategy = true;

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
            computeDoubleTree();
            computeDoubleTree();
        }
        else {
            MovementMap.makeMovement(board.lastMove());
            updateFenMap(board);
            computeTree();
        }
        computeMoves();

        Move bestResponse = getBestResponseCalculated(MovementMap.currentMoveFromTheGame, board);

        MovementMap.makeMovement(bestResponse);

        return bestResponse;
    }

    public void computeMoves() throws CloneNotSupportedException
    {
        if (numberStrategy) {
            allDepth(MovementMap.currentMoveFromTheGame, 4);
            number_of_computes = NUMBER_OF_MOVES - MovementMap.currentMoveFromTheGame.numberOfChildren();
            while (number_of_computes > 0) {
                computeMapExtra(MovementMap.currentMoveFromTheGame);
            }
        }
    }

    public void allDepth(MovementMap movementMap, int x) throws CloneNotSupportedException
    {
        if (movementMap.getMovementMap().isEmpty()) {
            if (x > 0) {
                computeMove(movementMap, 1);
            }
        }
        else {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                if (!movementMap1.getCurrentMove().isCheckMate()) {
                    allDepth(movementMap1, x - 1);
                }
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
            int new_value = getMovementMapScore(movementMap1, 20) + moveBonusScore(movementMap1, board);

            if (new_value > best_value) {
                System.out.println("new best value is " + new_value + " with new move " + movementMap1.getCurrentMove());
                best_value = new_value;
                bestResponse = movementMap1.getCurrentMove();

            }
        }
        return bestResponse;
    }

    private int moveBonusScore(MovementMap movementMap, Board board) throws Exception
    {
        int new_value = 0;
        Move move = movementMap.getCurrentMove();

        ScoreCalculator.resetScore();

        if (move.isCastleMove()) {
            ScoreCalculator.addCastleMoveScore();
        }

        if (MoveChecker.moveKnightFromFirstRow(move)) {
            ScoreCalculator.withKnightOnFirstRow();
        }

        if (move.getMovingPiece().getPieceType() == PAWN) {
            Position leftDiagonalPosition = move.getFinalPosition().leftDiagonal(move.getMovingPiece().isWhite());
            Position rightDiagonalPosition = move.getFinalPosition().rightDiagonal(move.getMovingPiece().isWhite());

            if (board.getMovingPiece(leftDiagonalPosition) != null) {
                new_value++;
            }
            if (board.getMovingPiece(rightDiagonalPosition) != null) {
                new_value++;
            }
        }
        if (MoveChecker.isPawnMove(move)) {
            ScoreCalculator.withPawnMove(Board.actualMoves.size());
        }

        Board board1 = movementMap.generateBoardForCurrentPosition();
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
        return new_value + ScoreCalculator.compute();
    }

    private int getMovementMapScore(MovementMap movementMap, int depth)
    {
        if (movementMap.getCurrentMove().isCheckMate()) {
            return GameOptions.CHECK_MATE_SCORE * depth;
        }
        if (movementMap.getCurrentMove().isStaleMate()) {
            return GameOptions.STALE_MATE_SCORE * depth;
        }
        movementMap.score = GameOptions.getSingleMoveScore(movementMap.getCurrentMove());

        int moveScore = GameOptions.simpleScore(movementMap.getCurrentMove());

        int maxResponseScore = movementMap.getMovementMap().values()
                .stream().map(it -> getMovementMapScore(it, depth - 1))
                .mapToInt(it -> it)
                .max().orElse(ZERO);
        movementMap.finalScore = moveScore - maxResponseScore;

        return moveScore - maxResponseScore;
    }

    public void computeDoubleTree() throws CloneNotSupportedException
    {
        if (!numberStrategy) {
            computeMap(MovementMap.currentMoveFromTheGame, 2);
        }
    }

    public void computeTree() throws CloneNotSupportedException
    {
        if (!numberStrategy) {
            computeMap(MovementMap.currentMoveFromTheGame, 1);
        }
    }

    public void computeMapExtra(MovementMap movementMap) throws CloneNotSupportedException
    {
        if (movementMap.getMovementMap().isEmpty() && number_of_computes > 0) {
            number_of_computes--;
            computeMove(movementMap, 2);
            number_of_computes -= movementMap.numberOfChildren();
        }
        else {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                if (!movementMap1.getCurrentMove().isCheckMate()) {
                    computeMapExtra(movementMap1);
                }
            }
        }
    }

    public void computeMap(MovementMap movementMap, int n) throws CloneNotSupportedException
    {
        if (movementMap.getMovementMap().isEmpty()) {
            computeMove(movementMap, n);
        }
        else {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                if (!movementMap1.getCurrentMove().isCheckMate()) {
                    computeMap(movementMap1, n);
                }
            }
        }
    }

    public void computeMove(MovementMap movementMap, int n) throws CloneNotSupportedException
    {
        if (n <= 0) {
            return;
        }
        Board board = movementMap.generateBoardForCurrentPosition();

        try {
            List<Move> possibleMovesCalculatorsList = board.calculatePossibleMoves();

            //is a checkmate move
            if (possibleMovesCalculatorsList.isEmpty()) {
                if (KingSafety.isTheKingAttacked(board)) {
                    movementMap.getCurrentMove().setCheckMate();
                }
                else {
                    movementMap.getCurrentMove().setStaleMate();
                }
            }
            else {
                for (Move move : possibleMovesCalculatorsList) {

                    MovementMap newMovementMap = new MovementMap(movementMap, move);
                    //todelete if too slow
                    //  updateMoveIfCheckMateOrStaleMate(newMovementMap, board);
                    movementMap.getMovementMap().put(move, newMovementMap);

                    computeMove(newMovementMap, n - 1);
                    if (newMovementMap.getCurrentMove().isCheckMate()) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(board);
            System.err.println(movementMap);
            e.printStackTrace();
        }
    }

    public void setup(Board board) throws InterruptedException
    {
        MovementMap.currentMoveFromTheGame = new SuperMovementMap(null, new Move(board.getKingPosition(), board.getKingPosition()), board);
        board.computePossibleMoves();
    }

    static class SuperMovementMap extends MovementMap
    {

        Board board;

        @Override
        public Board generateBoardForCurrentPosition() throws CloneNotSupportedException
        {
            return board;
        }

        public SuperMovementMap(MovementMap parent, Move currentMove, Board board)
        {
            super(parent, currentMove);
            this.board = board;
        }

        public SuperMovementMap(MovementMap parent, Move currentMove)
        {
            super(parent, currentMove);
        }
    }

}

package game;

import board.Board;
import board.MovementMap;
import board.Position;
import board.moves.Move;
import board.pieces.Piece;
import board.pieces.PieceType;
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

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;
    private static final int ZERO = 0;

    boolean hasDoubledDepth = false;

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
        }
        else {
            MovementMap.makeMovement(board.lastMove());
            updateFenMap(board);

        }
        computeDoubleTree();
        if (!hasDoubledDepth && board.getMovingPiecesMap().size() + board.getTakenPiecesMap().size() <= 7) {
            hasDoubledDepth = true;
            //   computeDoubleTree();
        }

        Move bestResponse = getBestResponseCalculated(MovementMap.currentMoveFromTheGame, board);

        MovementMap.makeMovement(bestResponse);


        return bestResponse;
    }

    public Move getBestResponseCalculatedSimple(MovementMap movementMap, Board board)
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

            movementMap1.final_score = new_value;
            movementMap1.score = GameOptions.getSingleMoveScore(movementMap1.getCurrentMove());
            if (new_value > best_value) {
                System.out.println("new best value is " + new_value + " with new move " + movementMap1.getCurrentMove());
                best_value = new_value;
                bestResponse = movementMap1.getCurrentMove();

            }
        }


        return bestResponse;
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
                new_value += 20;
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

            //ok
            if (move.getMovingPiece() != null && move.getMovingPiece().getPieceType() == PieceType.PAWN) {
                new_value += 1;
                if (Board.actualMoves.size() > 80) {
                    new_value += 1;
                }
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
        movementMap.score = GameOptions.getSingleMoveScore(movementMap.getCurrentMove());

        int moveScore = GameOptions.simpleScore(movementMap.getCurrentMove());

        int maxResponseScore = movementMap.getMovementMap().values()
                .stream().map(it -> getMovementMapScore(it, depth - 1))
                .mapToInt(it -> it)
                .max().orElse(ZERO);
        movementMap.final_score = moveScore - maxResponseScore;

        return moveScore - maxResponseScore;
    }

    public void computeDoubleTree() throws CloneNotSupportedException
    {
        computeMap(MovementMap.currentMoveFromTheGame, 2);
    }

    public void computeTree() throws CloneNotSupportedException
    {
        computeMap(MovementMap.currentMoveFromTheGame, 1);
    }

    public void computeMap(MovementMap movementMap, int n) throws CloneNotSupportedException
    {
        if (!isMovementMapValidForTheGame(movementMap)) {
            movementMap.markMovesAsImpossible();
            return;
        }
        if (movementMap.getMovementMap().isEmpty()) {
            computeMove(movementMap, n);
        }
        else {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                computeMap(movementMap1, n);
            }
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

    public int depthToCurrentMove(MovementMap movementMap)
    {
        int number = 0;
        MovementMap movementMap1 = movementMap;
        while (movementMap1 != null && movementMap1 != MovementMap.currentMoveFromTheGame) {
            number++;
            movementMap1 = movementMap1.getParent();
        }
        return number;
    }

    public void computeMove(MovementMap movementMap, int n) throws CloneNotSupportedException
    {
        int depth = depthToCurrentMove(movementMap);
        if(n<=0)
        {
            return;
        }
        if(depth>6)
        {
            return;
        }
        if (n <= 0 || depth > 4) {
            Piece takenPiece = movementMap.getCurrentMove().getTakenPiece();
            if (takenPiece == null || takenPiece.getScore() < 3) {
                return;
            }
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
                //todelete if too slow
                //  updateMoveIfCheckMateOrStaleMate(newMovementMap, board);
                movementMap.getMovementMap().put(move, newMovementMap);

                computeMove(newMovementMap, n - 1);
            }
        }
    }

    public void setup(Board board) throws InterruptedException
    {
        MovementMap.currentMoveFromTheGame = new SuperMovementMap(null, new Move(board.getKingPosition(), board.getKingPosition()), board);
        board.computePossibleMoves();
    }

    class SuperMovementMap extends MovementMap
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

package toDelete;

import board.Board;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import board.pieces.Piece;
import game.gameSetupOptions.GameOptions;

import java.util.*;

public class MovesCalculator
{

    public static Move calculate(Board board, int moves, int depth)
    {
        if (board.lastMove() != null && board.lastMove().isCheckMate()) {
            return GameOptions.checkMate();
        }
        board.computePossibleMoves();
        List<Move> moveList = new ArrayList<>(board.getPossibleMoves());
        for (Move move : moveList) {
            move.setScore(scoreCalculator(board, move));
        }
        Collections.sort(moveList);
        if (depth == 1) {
            if (moveList.isEmpty()) {
                Move move = GameOptions.checkMate();
            }
            Move bestMove = moveList.get(0);
            for (Move move : moveList) {
                if (scoreCalculator(board, move) > scoreCalculator(board, bestMove)) {
                    bestMove = move;
                }
            }
            bestMove.setScore(scoreCalculator(board, bestMove));
            return bestMove;
        }

        Move bestMove = new Move(-1000);

        int maxIndex = Math.min(moveList.size(), moves);

        moveList = moveList.subList(0, maxIndex);
        for (Move move : moveList) {
            //make the supposed move
            move.setScore(scoreCalculator(board, move));
            board.move(move);
            board.nextTurn();

            //compute the best response
            Move bestMoveForCurrent = calculate(board, moves, depth - 1);
            move.setScore(move.getScore() - bestMoveForCurrent.getScore());

            if (move.getScore() > bestMove.getScore()) {
                bestMove = move;
            }
            board.previousTurn();
            board.undoMove(move);
        }
        return bestMove;
    }

    private static int scoreCalculator(Board board, Move move)
    {
        if (move == null) {
            return 0;
        }
        MoveUpdateHelper.moveUpdate(board, move);

        Piece takenPiece = board.getTakenPiecesMap().get(move.getFinalPosition());
        if (takenPiece == null) {
            return 0;
        }

        return takenPiece.getScore();
    }


    public static Move calculate2(Board board, int moves, int depth)
    {
        board.computePossibleMoves();
        List<Move> moveList      = new ArrayList<>(sortedListMove(board));
        boolean    isWhiteToMove = board.isWhiteToMove();

        if (moveList.size() == 0) {
            if (board.lastMove().isCheckMate()) {
                return GameOptions.checkMate();
            }
            return GameOptions.staleMate();
        }
        if (moveList.size() == 1) {
            return moveList.get(0);
        }
        if (depth == 1) {
            return moveList.stream().max(Comparator.comparing(Move::getScore)).orElseThrow(NoSuchElementException::new);
        }
        Move bestMove = moveList.get(0);

        board.move(bestMove);
        board.nextTurn();
        Move bestResponse = calculate2(board, moves, depth - 1);

        bestMove.setScore(bestMove.getScore() - bestResponse.getScore());
        board.previousTurn();
        board.undoMove(bestMove);
        int remainingSize = Math.min(moves, moveList.size());

        moveList = moveList.subList(1, remainingSize);

        for (Move move : moveList) {
            board.move(move);
            board.setTurn(!isWhiteToMove);
            bestResponse = calculate2(board, moves, depth - 1);
            board.setTurn(isWhiteToMove);
            board.undoMove(move);

            move.setScore(move.getScore() - bestResponse.getScore());

            if (move.getScore() > bestMove.getScore()) {
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static List<Move> sortedListMove(Board board)
    {
        List<Move> moveList = board.getPossibleMoves();
        for (Move move : moveList) {
            MoveUpdateHelper.moveUpdate(board, move);
        }
        Collections.sort(moveList);

        return moveList;
    }
}

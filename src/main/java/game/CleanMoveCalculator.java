package game;

import board.Board;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class CleanMoveCalculator
{

    public static boolean precalculate = false;

    public static List<Move> calculateAllMoveBestResponse(Board board, int depth)
    {
        precalculate = true;
        boolean isWhiteToMove = board.isWhiteToMove();
        board.computePossibleMoves();
        List<Move> moveList   = new ArrayList<>(board.getPossibleMoves());
        List<Move> resultList = new ArrayList<>();


        for (Move move : moveList) {
            makeMove(board, move);
            Move bestResponse = calculate2(board, depth);
            move.setBestResponse(bestResponse);
           // log.info("Precomputed moves for " + move + " score is " + move.moveScore());
            undoMove(board, move, isWhiteToMove);
            move.moveScore();


            if (GameBoardHelper.isMyTurn()) {
            //    log.info("Precomputing stopped by actual move");
                return resultList;
            }
            resultList.add(move);

        }

        if (precalculate && resultList.size() > 0) {
            resultList.remove(resultList.size() - 1);
        }
        precalculate = false;
        return resultList;
    }


    public static Move calculate2(Board board, int depth)
    {
        final int currentDepth;
        if (board.lastMove() != null && board.lastMove().getTakenPiece() != null) {
            currentDepth = depth;
        }
        else {
            currentDepth = depth;
        }
        List<Move> moveList;
        if (board.getPossibleMoves() == null) {
            board.computePossibleMoves();
            moveList = board.getPossibleMoves();
        }
        else {
            moveList = board.calculatePossibleMoves();
        }
        //extract a portion of moves
        moveList = GameOptions.extractMoves(moveList, currentDepth);

        boolean isWhiteToMove = board.isWhiteToMove();

        //stalemate or checkmate
        if (moveList.isEmpty()) {
            if (KingSafety.isTheKingAttacked(board)) {
                return GameOptions.checkMate(depth);
            }
            return GameOptions.staleMate();
        }
        //forcedMove
        //todo: check if this is needed
        if (isForcedMove(moveList)) {
            return moveList.get(0);
        }
        if (currentDepth == 1) {
            return moveList.stream().peek(move -> MoveUpdateHelper.moveUpdate(board, move))
                    .max(Comparator.comparing(Move::getScore))
                    .orElseThrow(NoSuchElementException::new);
        }
        int index  = 0;
        int length = moveList.size();

        return moveList.stream().peek(move -> {
            if (precalculate && GameBoardHelper.isMyTurn()) {
                return;
            }
            board.setTurn(isWhiteToMove);
            makeMove(board, move);
            Move bestResponse = calculate2(board, currentDepth - 1);
            undoMove(board, move, isWhiteToMove);
            move.setBestResponse(bestResponse);
            move.setScore(move.getScore()- bestResponse.getActualScore());
            if (GameBoard.depth == currentDepth) {
            //    log.info("Computed score for move:" + move + " " + "score is " + move.moveScore() + "(" + index + "/" + length + ")");
            }
        }).max(Comparator.comparing(Move::getActualScore))
                .orElseThrow(NoSuchElementException::new);
    }

    private static void makeMove(Board board, Move move)
    {
        MoveUpdateHelper.moveUpdate(board, move);
        board.move(move);
        board.nextTurn();
    }

    private static void undoMove(Board board, Move move, boolean isWhiteToMove)
    {
        board.setTurn(isWhiteToMove);
        board.undoMove(move);
    }

    private static boolean isForcedMove(List<Move> moveList)
    {
        return moveList.size() == 1;
    }
}

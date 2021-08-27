package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.gameSetupOptions.GameOptions;
import game.kingcheck.attacked.KingSafety;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static game.GameBoard.getMyOwnGoingGames;

@Slf4j
public class CleanMoveCalculator
{

    public static boolean precalculate = false;

    public static List<Move> calculateAllMoveBestResponse(OptimizedBoard optimizedBoard, int depth)
    {
        precalculate = true;
        boolean isWhiteToMove = optimizedBoard.isWhiteToMove();
        optimizedBoard.computePossibleMoves();
        List<Move> moveList   = new ArrayList<>(optimizedBoard.getPossibleMoves());
        List<Move> resultList = new ArrayList<>();


        for (Move move : moveList) {
            makeMove(optimizedBoard, move);
            Move bestResponse = calculate2(optimizedBoard, depth);
            move.setBestResponse(bestResponse);
            log.info("Precomputed moves for " + move + " score is " + move.moveScore());
            undoMove(optimizedBoard, move, isWhiteToMove);
            move.moveScore();


            if (!GameBoard.waitingForOpponentMove()) {
                log.info("Precomputing stopped by actual move");
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


    public static Move calculate2(OptimizedBoard optimizedBoard, int depth)
    {
        final int currentDepth;
        if (optimizedBoard.lastMove() != null && optimizedBoard.lastMove().getTakenPiece() != null) {
            currentDepth = depth;
        }
        else {
            currentDepth = depth;
        }
        List<Move> moveList;
        if (optimizedBoard.getPossibleMoves() == null) {
            optimizedBoard.computePossibleMoves();
            moveList = optimizedBoard.getPossibleMoves();
        }
        else {
            moveList = optimizedBoard.calculatePossibleMoves();
        }
        //extract a portion of moves
        moveList = GameOptions.extractMoves(moveList, currentDepth);

        boolean isWhiteToMove = optimizedBoard.isWhiteToMove();

        //stalemate or checkmate
        if (moveList.size() == 0) {
            if (KingSafety.getNumberOfAttackers(optimizedBoard) > 0) {
                return GameOptions.checkMate(depth);
            }
            return GameOptions.staleMate();
        }
        //forcedMove
        if (isForcedMove(moveList)) {
            return moveList.get(0);
        }
        if (currentDepth == 1) {
            return moveList.stream().peek(move -> MoveUpdateHelper.moveUpdate(optimizedBoard, move))
                    .max(Comparator.comparing(Move::getScore))
                    .orElseThrow(NoSuchElementException::new);
        }
        int index  = 0;
        int length = moveList.size();

        return moveList.stream().peek(move -> {
            if (precalculate) {
                return;
            }
            optimizedBoard.setTurn(isWhiteToMove);
            makeMove(optimizedBoard, move);
            Move bestResponse = calculate2(optimizedBoard, currentDepth - 1);
            undoMove(optimizedBoard, move, isWhiteToMove);
            move.setBestResponse(bestResponse);
            move.moveScore();
            if (GameBoard.depth == currentDepth) {
                log.info("Computed score for move:" + move + " " + "score is " + move.moveScore() + "(" + index + "/" + length + ")");
            }
        }).max(Comparator.comparing(Move::moveScore))
                .orElseThrow(NoSuchElementException::new);
    }

    private static void makeMove(OptimizedBoard optimizedBoard, Move move)
    {
        MoveUpdateHelper.moveUpdate(optimizedBoard, move);
        optimizedBoard.move(move);
        optimizedBoard.nextTurn();
    }

    private static void undoMove(OptimizedBoard optimizedBoard, Move move, boolean isWhiteToMove)
    {
        optimizedBoard.setTurn(isWhiteToMove);
        optimizedBoard.undoMove(move);
    }

    private static boolean isForcedMove(List<Move> moveList)
    {
        return moveList.size() == 1;
    }
}

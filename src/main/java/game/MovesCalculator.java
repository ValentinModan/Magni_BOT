package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import board.pieces.Piece;

import java.util.*;

public class MovesCalculator
{
    public static Move bestMoveOfAll;

    public static Move getBestMove(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        return null;
    }

    public static Move calculate(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        if (optimizedBoard.lastMove() != null && optimizedBoard.lastMove().isCheckMate()) {
            return new Move(-1000);
        }
        optimizedBoard.computePossibleMoves();
        List<Move> moveList = new ArrayList<>(optimizedBoard.getPossibleMoves());
        for (Move move : moveList) {
            move.setScore(scoreCalculator(optimizedBoard, move));
        }
        Collections.sort(moveList);
        if (depth == 1) {
            if (moveList.size() == 0) {
                return new Move(true);
            }
            Move bestMove = moveList.get(0);
            for (Move move : moveList) {
                if (scoreCalculator(optimizedBoard, move) > scoreCalculator(optimizedBoard, bestMove)) {
                    bestMove = move;
                }

            }
            bestMove.setScore(scoreCalculator(optimizedBoard, bestMove));
            return bestMove;
        }

        Move bestMove = new Move(-1000);

        int maxIndex = Math.min(moveList.size(), moves);

        moveList = moveList.subList(0, maxIndex);
        for (Move move : moveList) {
            //make the supposed move
            move.setScore(scoreCalculator(optimizedBoard, move));
            optimizedBoard.move(move);
            optimizedBoard.nextTurn();

            //compute the best response
            Move bestMoveForCurrent = calculate(optimizedBoard, moves, depth - 1);
            move.setScore(move.getScore() - bestMoveForCurrent.getScore());

            if (move.getScore() > bestMove.getScore()) {
                bestMove = move;
            }
            optimizedBoard.previousTurn();
            optimizedBoard.undoMove(move);
        }
        return bestMove;
    }

    private static int scoreCalculator(OptimizedBoard optimizedBoard, Move move)
    {
        if (move == null) {
            return 0;
        }
        MoveUpdateHelper.moveUpdate(optimizedBoard, move);

        Piece takenPiece = optimizedBoard.getTakenPiecesMap().get(move.getFinalPosition());
        if (takenPiece == null) {
            return 0;
        }

        return takenPiece.getScore();
    }


    public static Move calculate2(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        optimizedBoard.computePossibleMoves();
        List<Move> moveList      = new ArrayList<>(sortedListMove(optimizedBoard));
        boolean    isWhiteToMove = optimizedBoard.isWhiteToMove();

        if (moveList.size() == 0) {
            if(optimizedBoard.lastMove().isCheckMate())
            {
                return new Move(-10000);
            }
            return new Move(-100);
        }
        if (moveList.size() == 1) {
            return moveList.get(0);
        }
        if (depth == 1) {
            return moveList.stream().max(Comparator.comparing(Move::getScore)).orElseThrow(NoSuchElementException::new);
        }
        Move bestMove = moveList.get(0);

        optimizedBoard.move(bestMove);
        optimizedBoard.nextTurn();
        Move bestResponse = calculate2(optimizedBoard, moves, depth - 1);
        bestMove.setScore(bestMove.getScore() - bestResponse.getScore());
        optimizedBoard.previousTurn();
        optimizedBoard.undoMove(bestMove);
        int remainingSize = Math.min(moves, moveList.size());

        moveList = moveList.subList(1, remainingSize);

        for (Move move : moveList) {
            optimizedBoard.move(move);
            optimizedBoard.setTurn(!isWhiteToMove);
            bestResponse = calculate2(optimizedBoard, moves, depth - 1);
            optimizedBoard.setTurn(isWhiteToMove);
            optimizedBoard.undoMove(move);

            move.setScore(move.getScore() - bestResponse.getScore());

            if (move.getScore() > bestMove.getScore()) {
                bestMove = move;
//                if (depth == GameBoard.DEPTH && bestMove.getScore() >= 0) {
//                    return bestMove;
//                }
            }
        }
        return bestMove;
    }

    private static List<Move> sortedListMove(OptimizedBoard optimizedBoard)
    {
        List<Move> moveList = optimizedBoard.getPossibleMoves();
        for (Move move : moveList) {
            MoveUpdateHelper.moveUpdate(optimizedBoard, move);
        }
        Collections.sort(moveList);

        return moveList;
    }
}

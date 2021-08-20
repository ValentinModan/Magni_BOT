package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.pieces.Piece;

import java.util.*;

public class MovesCalculator
{
    public static Move calculate(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        if(optimizedBoard.lastMove()!=null && optimizedBoard.lastMove().isCheckMate())
        {
            return new Move(-1000);
        }
        optimizedBoard.computePossibleMoves();
        List<Move> moveList = optimizedBoard.getPossibleMoves();
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

        int maxIndex = Math.min(optimizedBoard.getPossibleMoves().size(), moves);

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
        if(move == null)
        {
            return 0;
        }
        Piece takenPiece = optimizedBoard.getTakenPiecesMap().get(move.getFinalPosition());
        if (takenPiece == null) {
            return 0;
        }

        return takenPiece.getScore();
    }

}

package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.pieces.Piece;

import java.util.*;

public class MovesCalculator
{
    public static Move calculate(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        optimizedBoard.computePossibleMoves();
        List<Move> moveList = optimizedBoard.getPossibleMoves();

        if (depth == 1) {
            if(moveList.size()==0)
            {
                return new Move(-100000);
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

        for (Move move : moveList) {
            move.setScore(scoreCalculator(optimizedBoard, move));
        }
        int maxIndex = Math.min(optimizedBoard.getPossibleMoves().size(), moves);
        Collections.sort(moveList);

        moveList = moveList.subList(0, maxIndex);
        for (Move move : moveList) {
            //make the supposed move
            optimizedBoard.move(move);
            move.setScore(scoreCalculator(optimizedBoard, move));
            optimizedBoard.setWhiteToMove(!optimizedBoard.isWhiteToMove());

            //compute the best response
            Move bestMoveForCurrent = calculate(optimizedBoard, moves, depth - 1);
            move.setScore(move.getScore() - bestMoveForCurrent.getScore());

            if (move.getScore() > bestMove.getScore()) {
                bestMove = move;
            }
            optimizedBoard.setWhiteToMove(!optimizedBoard.isWhiteToMove());
            optimizedBoard.undoMove(move);
        }
        return bestMove;
    }

    private static int scoreCalculator(OptimizedBoard optimizedBoard, Move move)
    {
        Piece takenPiece = optimizedBoard.getTakenPiecesMap().get(move.getFinalPosition());
        if (takenPiece == null) {
            return 0;
        }

        return takenPiece.getScore();
    }

}

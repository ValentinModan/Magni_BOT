package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class MovesCalculator
{


    public static Move calculate(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        Map<Move, Integer> moveScoreMap = new HashMap<>();

        optimizedBoard.computePossibleMoves();

        if (depth == 1) {
            Move bestMove = optimizedBoard.getPossibleMoves().get(0);
            for (Move move : optimizedBoard.getPossibleMoves()) {
                if (scoreCalculator(optimizedBoard, move) > scoreCalculator(optimizedBoard, bestMove)) {
                    bestMove = move;
                }

            }
            return bestMove;
        }

        Move bestMove = new Move(-1000);
        for (Move move : optimizedBoard.getPossibleMoves()) {
            optimizedBoard.move(move);
            optimizedBoard.setWhiteToMove(!optimizedBoard.isWhiteToMove());
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
        Piece takenPiece  = optimizedBoard.getPiece(move.getFinalPosition());
        if (takenPiece == null) {
            return 0;
        }

        return takenPiece.getScore();
    }

}

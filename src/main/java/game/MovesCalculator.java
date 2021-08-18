package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.pieces.Piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MovesCalculator
{
    public static Move calculate(OptimizedBoard optimizedBoard, int moves, int depth)
    {
        optimizedBoard.computePossibleMoves();
       /* if(depth == 1)
        {
            int random = new Random().nextInt(optimizedBoard.getPossibleMoves().size());
            return optimizedBoard.getPossibleMoves().get(random);
        }*/

        if (depth == 1) {
            Move bestMove = optimizedBoard.getPossibleMoves().get(0);
            for (Move move : optimizedBoard.getPossibleMoves()) {
                if (scoreCalculator(optimizedBoard, move) > scoreCalculator(optimizedBoard, bestMove)) {
                    bestMove = move;
                }

            }
            bestMove.setScore(scoreCalculator(optimizedBoard,bestMove));
            return bestMove;
        }

        Move bestMove = new Move(-1000);
        for (Move move : optimizedBoard.getPossibleMoves()) {
            //make the supposed move
            optimizedBoard.move(move);
            move.setScore(scoreCalculator(optimizedBoard,move));
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

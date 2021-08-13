package board.possibleMoves.resource;

import board.OptimizedBoard;
import board.moves.Move;

import java.util.List;

public class DepthCalculator
{

    public static int possibleMoves(OptimizedBoard board, int depth)
    {
        if (depth == 0) {
            return 1;
        }

        int moves = 0;

        board.computePossibleMoves();

        List<Move> moveList = board.getPossibleMoves();

        for (Move move : moveList) {
            board.move(move);
            board.setWhiteToMove(!board.isWhiteToMove());
            moves += possibleMoves(board, depth - 1);
            board.setWhiteToMove(!board.isWhiteToMove());
            board.undoMove(move);
        }
        return moves;
    }
}

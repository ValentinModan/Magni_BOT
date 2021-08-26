package board.possibleMoves.resource;

import board.OptimizedBoard;
import board.moves.Move;
import game.kingcheck.attacked.KingSafety;

import java.util.List;

public class DepthCalculator
{
    public static double seconds = 0;
    public static double check_verification_seconds = 0;
    public static int possibleMoves(OptimizedBoard board, int depth)
    {
        if (depth == 0) {
            return 1;
        }

        int moves = 0;
        long start = System.nanoTime();
        board.computePossibleMoves();
        long elapsedTime = System.nanoTime() - start;
        seconds+=(double)elapsedTime / 1_000_000_000.0;
        List<Move> moveList = board.getPossibleMoves();

        for (Move move : moveList) {
            if (depth == 6) {
                System.out.println(moves);
            }

            board.move(move);

            if (KingSafety.getNumberOfAttackers(board) == 0) {
                board.nextTurn();
                moves += possibleMoves(board, depth - 1);
                board.nextTurn();
            }
            board.undoMove(move);
        }
        return moves;
    }
}

package board.possibleMoves.resource;

import board.Board;
import board.moves.Move;
import game.kingcheck.attacked.KingSafety;

import java.util.List;

public class DepthCalculator
{
    public static double seconds = 0;
    public static double check_verification_seconds = 0;
    public static int possibleMoves(Board board, int depth)
    {
        if (depth == 0) {
            return 1;
        }

        int moves = 0;
        long start = System.nanoTime();
       // board.computePossibleMoves();
        long elapsedTime = System.nanoTime() - start;
        seconds+=(double)elapsedTime / 1_000_000_000.0;
        List<Move> moveList = board.calculatePossibleMoves();

        for (Move move : moveList) {
            if (depth == 6) {
                System.out.println(moves);
            }

            board.move(move);

            if (!KingSafety.isTheKingAttacked(board)) {
                board.nextTurn();
                moves += possibleMoves(board, depth - 1);
                board.nextTurn();
            }
            board.undoMove(move);
        }
        return moves;
    }
}

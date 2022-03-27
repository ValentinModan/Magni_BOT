package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;

import java.util.List;

public class KnightMoveCalculator extends PieceMoveCalculator {

    private static final KnightMoveCalculator knightMoveCalculator = new KnightMoveCalculator();

    private KnightMoveCalculator()
    {
    }

    public static KnightMoveCalculator getInstance()
    {
        return knightMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        return super.computeMoves(board,position);
    }
}

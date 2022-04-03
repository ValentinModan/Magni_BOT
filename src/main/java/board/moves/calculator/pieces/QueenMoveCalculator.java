package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.Movement;
import board.moves.pieces.QueenMovement;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class QueenMoveCalculator extends PieceMoveCalculator
{
    private static final QueenMoveCalculator queenMoveCalculator = new QueenMoveCalculator();

    private QueenMoveCalculator()
    {
    }

    public static QueenMoveCalculator getInstance()
    {
        return queenMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        return Xray.xRayMoveList(board, position, QueenMovement.getInstance().getMovements());
    }
}

package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.Movement;
import board.moves.pieces.BishopMovement;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class BishopMoveCalculator extends PieceMoveCalculator
{
    private static final BishopMoveCalculator bishopMoveCalculator = new BishopMoveCalculator();

    private BishopMoveCalculator() {}

    public static BishopMoveCalculator getInstance()
    {
        return bishopMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        return Xray.xRayMoveList(board, position, BishopMovement.getInstance().getMovements());
    }
}

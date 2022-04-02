package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.moves.pieces.RookMovement;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class RookMoveCalculator extends PieceMoveCalculator
{
    private static final RookMoveCalculator rookMoveCalculator = new RookMoveCalculator();

    private RookMoveCalculator()
    {
    }

    public static RookMoveCalculator getInstance()
    {
        return rookMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position)
    {
        List<Movement> movementList = RookMovement.getInstance().getMovements();
        return Xray.xRayMoveList(board, position, movementList);
    }
}

package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class RookMoveCalculator extends PieceMoveCalculator
{
    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Movement> movementList = MovementCalculator.getPossibleMoves(board.getMovingPiece(position));
        return Xray.xRayMoveList(board, position, movementList);
    }
}

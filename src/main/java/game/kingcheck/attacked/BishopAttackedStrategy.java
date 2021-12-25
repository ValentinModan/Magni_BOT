package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.PieceType;

import java.util.List;

public class BishopAttackedStrategy implements AttackedStrategy
{

    final static List<Movement> bishopMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.BISHOP);

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board)
    {
        Position kingPosition = board.getKingPosition();
        for (Movement movement : bishopMovementList) {
            if (Xray.isXRayAttacked(board, kingPosition, movement, PieceType.BISHOP)) {
                return true;
            }
        }
        return false;
    }
}

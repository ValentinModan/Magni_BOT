package game.kingcheck.attacked;

import board.Board;
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
    public boolean isAttackingTheKing(Board board)
    {
        for (Movement movement : bishopMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.BISHOP)) {
                return true;
            }
        }
        return false;
    }
}

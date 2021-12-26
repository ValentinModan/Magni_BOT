package game.kingcheck.attacked;

import board.Board;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.PieceType;

import java.util.List;

public class RookAttackedStrategy implements AttackedStrategy
{

    private static final List<Movement> rookMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.ROOK);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        for (Movement movement : rookMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.ROOK)) {
                return true;
            }
        }
        return false;
    }
}

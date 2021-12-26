package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.PieceType;

import java.util.List;

public class QueenAttackedStrategy implements AttackedStrategy
{
    private static final List<Movement> queenMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.QUEEN);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        for (Movement movement : queenMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.QUEEN)) {
                return true;
            }
        }
        return false;
    }
}

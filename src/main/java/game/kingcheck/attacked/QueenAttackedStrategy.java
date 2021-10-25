package game.kingcheck.attacked;

import board.OptimizedBoard;
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
    public boolean isAttackingTheKing(OptimizedBoard board)
    {

        boolean  isWhiteKing  = board.isWhiteToMove();
        Position kingPosition = board.getKingPosition(isWhiteKing);

        for (Movement movement : queenMovementList) {
            if (Xray.isXRayAttacked(board, kingPosition, movement, isWhiteKing, PieceType.QUEEN)) {
                return true;
            }
        }
        return false;

    }
}

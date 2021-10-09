package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.PieceType;

import java.util.List;

public class RookAttackedStrategy implements AttackedStrategy {

    private static final List<Movement> rookMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.ROOK);

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {

        boolean isWhiteKing = board.isWhiteToMove();
        Position kingPosition = board.getKingPosition(isWhiteKing);

        for (Movement movement : rookMovementList) {
            if (Xray.isXRayAttacked(board, kingPosition, movement, isWhiteKing, PieceType.ROOK)) {
                return true;
            }
        }
        return false;
    }
}

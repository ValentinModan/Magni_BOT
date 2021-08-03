package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.PawnMovement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class PawnAttackedStrategy implements AttackedStrategy {

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {
        boolean isWhiteKing = board.isWhiteToMove();

        Position kingPosition = board.getKing(isWhiteKing);

        List<Movement> movementList = PawnMovement.attackMovements(isWhiteKing);

        for (Movement movement : movementList) {
            Piece possibleAttackingPiece = board.getPiece(kingPosition.move(movement));
            if (possibleAttackingPiece != null &&
                    possibleAttackingPiece.getPieceType() == PieceType.PAWN &&
                    possibleAttackingPiece.isWhite() != isWhiteKing) {
                return true;
            }
        }
        return false;
    }
}

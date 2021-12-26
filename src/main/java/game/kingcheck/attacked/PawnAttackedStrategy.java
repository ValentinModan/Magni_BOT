package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.PawnMovement;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class PawnAttackedStrategy implements AttackedStrategy
{
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        Position       kingPosition = board.getKingPosition();
        King           king         = (King) board.getPiece(kingPosition);
        List<Movement> movementList = PawnMovement.attackMovements(board.isWhiteToMove());

        for (Movement movement : movementList) {
            Piece possibleAttackingPiece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (possibleAttackingPiece != null &&
                    possibleAttackingPiece.getPieceType() == PieceType.PAWN &&
                    possibleAttackingPiece.isOpponentOf(king)) {
                return true;
            }
        }
        return false;
    }
}

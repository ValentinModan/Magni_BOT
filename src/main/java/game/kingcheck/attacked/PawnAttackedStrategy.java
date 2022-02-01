package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.PawnMovement;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class PawnAttackedStrategy implements AttackedStrategy
{
    @SneakyThrows
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        List<PieceType> list = new ArrayList<>();
        for (Piece piece : board.getTakenPiecesMap().values()) {
            if(piece==null)
            {
                continue;
            }
            PieceType pieceType = piece.getPieceType();
            if (pieceType != null) {
                list.add(pieceType);
            }
        }
        if (!list.contains(PieceType.PAWN)) {
            return false;
        }
        Position       kingPosition = board.getKingPosition();
        King           king         = (King) board.getPieceAt(kingPosition);
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

package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.PawnMovement;
import board.pieces.EmptyPiece;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

public class PawnAttackedStrategy implements AttackedStrategy
{
    @SneakyThrows
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(PieceType.PAWN)) {
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

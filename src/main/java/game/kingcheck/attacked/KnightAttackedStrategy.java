package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KnightAttackedStrategy implements AttackedStrategy
{

    private static final List<Movement> knightsPossibleMovements = MovementCalculator.getPossibleMoves(EmptyPiece.KNIGHT);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream().filter(Objects::nonNull)
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(PieceType.KNIGHT)) {
            return false;
        }
        Position kingPosition = board.getKingPosition();
        King king = board.getKing();

        for (Movement movement : knightsPossibleMovements) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null &&
                    piece.getPieceType() == PieceType.KNIGHT &&
                    piece.isOpponentOf(king)) {
                return true;
            }
        }
        return false;
    }
}

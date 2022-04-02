package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.KnightMovement;
import board.moves.pieces.MovementCalculator;
import board.pieces.King;
import board.pieces.Piece;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static board.pieces.PieceType.KNIGHT;

public class KnightAttackStrategy implements AttackStrategy
{

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream().filter(Objects::nonNull)
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(KNIGHT)) {
            return false;
        }
        Position kingPosition = board.getKingPosition();
        King king = board.getKing();

        for (Movement movement : KnightMovement.getInstance().getMovements()) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null &&
                    piece.getPieceType() == KNIGHT &&
                    piece.isOpponentOf(king)) {
                return true;
            }
        }
        return false;
    }
}

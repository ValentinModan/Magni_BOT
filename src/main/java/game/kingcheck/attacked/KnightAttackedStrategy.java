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

public class KnightAttackedStrategy implements AttackedStrategy
{

    private static final List<Movement> knightsPossibleMovements = MovementCalculator.getPossibleMoves(EmptyPiece.KNIGHT);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        Position kingPosition = board.getKingPosition(board.isWhiteToMove());
        King     king         = (King) board.getPiece(kingPosition);

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

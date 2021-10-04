package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.piecees.EmptyPiece;
import board.piecees.Piece;
import board.piecees.PieceType;

import java.util.List;

public class KingAttackedStrategy implements AttackedStrategy
{
    private static final List<Movement> kingAttackedStrategy = MovementCalculator.getPossibleMoves(EmptyPiece.KING);

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board)
    {
        Position kingPosition = board.getKingPosition(board.isWhiteToMove());

        for (Movement movement : kingAttackedStrategy) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null && piece.getPieceType() == PieceType.KING && piece.isWhite() != board.isWhiteToMove()) {
                return true;
            }
        }
        return false;
    }
}

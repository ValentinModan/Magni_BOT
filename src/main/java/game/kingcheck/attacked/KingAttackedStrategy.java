package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

//Makes sure not to make illegal move
public class KingAttackedStrategy implements AttackedStrategy
{
    private static final List<Movement> kingAttackedStrategy = MovementCalculator.getPossibleMoves(EmptyPiece.KING);

    @Override
    public boolean isAttackingTheKing(Board board)
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

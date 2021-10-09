package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class KnightAttackedStrategy implements AttackedStrategy {

    private static final List<Movement> knightsPossibleMovements = MovementCalculator.getPossibleMoves(EmptyPiece.KNIGHT);

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {
        Position kingPosition = board.getKingPosition(board.isWhiteToMove());


        for (Movement movement : knightsPossibleMovements) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null && piece.getPieceType() == PieceType.KNIGHT && piece.isWhite() != board.isWhiteToMove()) {
                return true;
            }
        }
        return false;
    }
}

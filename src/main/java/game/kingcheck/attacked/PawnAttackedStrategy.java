package game.kingcheck.attacked;

import board.Board;
import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.Arrays;
import java.util.List;

public class PawnAttackedStrategy implements AttackedStrategy {


    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {
        boolean isWhiteKing = board.isWhiteToMove();

        Position kingPosition = board.getKing(isWhiteKing);

        List<Movement> movementList = getMovementList(isWhiteKing);

        for (Movement movement : movementList) {
            Piece possibleAttackingPiece = board.getPiece(kingPosition.move(movement));
            if (possibleAttackingPiece != null && possibleAttackingPiece.getPieceType() == PieceType.PAWN && possibleAttackingPiece.isWhite() != isWhiteKing) {
                return true;
            }
        }
        return false;
    }

    private List<Movement> getMovementList(boolean isWhiteKing) {
        if (isWhiteKing) {
            return Arrays.asList(Movement.UP_LEFT, Movement.UP_RIGHT);
        }
        return Arrays.asList(Movement.LEFT_DOWN, Movement.DOWN_RIGHT);
    }
}

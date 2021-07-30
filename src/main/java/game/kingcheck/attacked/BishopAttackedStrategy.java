package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BishopAttackedStrategy implements AttackedStrategy {

    List<Movement> bishopMovementList = new ArrayList<>(Arrays.asList(
            Movement.UP_LEFT,
            Movement.DOWN_LEFT,
            Movement.DOWN_RIGHT,
            Movement.UP_RIGHT
    ));

    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {
        Position kingPosition = board.getKing(isWhiteKing);
        for (Movement movement : bishopMovementList) {
            if (isDiagonallyAttacked(board, kingPosition, movement, isWhiteKing))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isDiagonallyAttacked(Board board, Position currentPosition, Movement movement, boolean isWhiteKing) {
        currentPosition = currentPosition.move(movement);
        if (!currentPosition.isValid()) {
            return false;
        }
        Piece piece = board.getPosition(currentPosition);
        if (piece != null) {
            return piece.isBishop() && piece.isWhite() != isWhiteKing;
        }
        return isDiagonallyAttacked(board, currentPosition, movement, isWhiteKing);
    }
}

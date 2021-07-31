package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;

import java.util.function.Function;

public class XrayAttack {

    public static boolean isXRayAttacked(Board board, Position currentPosition, Movement movement, boolean isWhiteKing, Function<Piece, Boolean> predicate) {
        currentPosition = currentPosition.move(movement);
        if (!currentPosition.isValid()) {
            return false;
        }
        Piece piece = board.getPosition(currentPosition);
        if (piece != null) {
            return predicate.apply(piece) && piece.isWhite() != isWhiteKing;
        }
        return isXRayAttacked(board, currentPosition, movement, isWhiteKing,predicate);
    }
}

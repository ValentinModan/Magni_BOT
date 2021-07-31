package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RookAttackedStrategy implements AttackedStrategy {

    private static final List<Movement> rookMovementList = new ArrayList<>(Arrays.asList(
            Movement.UP,
            Movement.LEFT,
            Movement.DOWN,
            Movement.RIGHT
    ));

    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {
        Position kingPosition = board.getKing(isWhiteKing);

        for (Movement movement : rookMovementList) {
            if (XrayAttack.isXRayAttacked(board, kingPosition, movement, isWhiteKing, Piece::isRook)) {
                return true;
            }
        }
        return false;
    }
}

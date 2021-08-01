package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.pieces.PieceType;

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
    public boolean isAttackingTheKing(OptimizedBoard board) {

        boolean isWhiteKing = board.isWhiteToMove();
        Position kingPosition = board.getKing(isWhiteKing);

        for (Movement movement : rookMovementList) {
            if (XrayAttack.isXRayAttacked(board, kingPosition, movement, isWhiteKing, PieceType.ROOK)) {
                return true;
            }
        }
        return false;
    }
}

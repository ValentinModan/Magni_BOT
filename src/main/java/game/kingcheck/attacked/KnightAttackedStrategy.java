package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightAttackedStrategy implements AttackedStrategy {

    private static final List<Movement> knightsPossibleMovements = new ArrayList<>(Arrays.asList(
            Movement.KNIGHT_UP_LEFT,
            Movement.KNIGHT_LEFT_UP,
            Movement.KNIGHT_LEFT_DOWN,
            Movement.KNIGHT_DOWN_LEFT,
            Movement.KNIGHT_DOWN_RIGHT,
            Movement.KNIGHT_RIGHT_DOWN,
            Movement.KNIGHT_RIGHT_UP,
            Movement.KNIGHT_UP_RIGHT));

    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {
        Position kingPosition = board.getKing(isWhiteKing);

        for (Movement movement : knightsPossibleMovements) {
            Piece piece = board.getPosition(kingPosition.move(movement));
            if(piece!=null && piece.isKnight() && piece.isWhite() != isWhiteKing)
            {
                return true;
            }
        }
        return false;
    }
}
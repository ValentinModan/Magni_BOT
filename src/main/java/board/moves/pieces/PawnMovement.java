package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Pawn;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PawnMovement implements PieceMovement {

    private static final List<Movement> whiteMovementList = new ArrayList<>(Arrays.asList(Movement.UP_LEFT, Movement.UP, Movement.UP_RIGHT));
    private static final List<Movement> blackMovementList = new ArrayList<>(Arrays.asList(Movement.LEFT_DOWN, Movement.DOWN, Movement.DOWN_RIGHT));

    private static final List<Movement> whiteAttackList = new ArrayList<>(Arrays.asList(Movement.UP_LEFT, Movement.UP_RIGHT));
    private static final List<Movement> blackAttackList = new ArrayList<>(Arrays.asList(Movement.LEFT_DOWN, Movement.DOWN_RIGHT));

    @Override
    public List<Movement> getMovements(Piece piece) {

        Pawn pawn = (Pawn) piece;
        List<Movement> movementList = piece.isWhite() ? new ArrayList<>(whiteMovementList) : new ArrayList<>(blackMovementList);

        if (!pawn.hasMoved()) {
            movementList.add(pawn.isWhite() ? Movement.UP_TWO : Movement.DOWN_TWO);
        }
        return movementList;
    }

    public static List<Movement> attackMovements(Boolean isWhite) {
        return isWhite ? whiteAttackList : blackAttackList;
    }

}

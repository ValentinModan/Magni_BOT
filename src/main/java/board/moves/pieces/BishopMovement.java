package board.moves.pieces;

import board.moves.Movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BishopMovement implements PieceMovement{

    private static final List<Movement> bishopMovementList = new ArrayList<>(Arrays.asList(
            Movement.UP_LEFT,
            Movement.LEFT_DOWN,
            Movement.DOWN_RIGHT,
            Movement.UP_RIGHT
    ));

    private static final BishopMovement bishopMovement = new BishopMovement();

    private BishopMovement()
    {
    }

    public static BishopMovement getInstance()
    {
        return bishopMovement;
    }

    @Override
    public List<Movement> getMovements() {
        return bishopMovementList;
    }
}

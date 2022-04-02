package board.moves.pieces;

import board.moves.Movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhitePawnMovement implements PieceMovement
{
    private static final List<Movement> whiteAttackList = new ArrayList<>(Arrays.asList(Movement.UP_LEFT, Movement.UP_RIGHT));

    private static final List<Movement> whiteMovementList = new ArrayList<>(Arrays.asList(Movement.UP_LEFT,
                                                                                          Movement.UP,
                                                                                          Movement.UP_RIGHT,
                                                                                          Movement.UP_TWO));

    private static final WhitePawnMovement whitePawnMovement = new WhitePawnMovement();

    private WhitePawnMovement()
    {

    }

    public static WhitePawnMovement getInstance()
    {
        return whitePawnMovement;
    }

    @Override
    public List<Movement> getMovements()
    {
        return whiteMovementList;
    }

    public static List<Movement> attackMovements()
    {
        return whiteAttackList;
    }
}

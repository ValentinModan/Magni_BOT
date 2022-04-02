package board.moves.pieces;

import board.moves.Movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlackPawnMovement implements PieceMovement
{
    private static final List<Movement> blackAttackList = new ArrayList<>(Arrays.asList(Movement.LEFT_DOWN, Movement.DOWN_RIGHT));

    private static final List<Movement> blackMovementList = new ArrayList<>(Arrays.asList(Movement.LEFT_DOWN,
                                                                                          Movement.DOWN,
                                                                                          Movement.DOWN_RIGHT,
                                                                                          Movement.DOWN_TWO));

    private static final BlackPawnMovement blackPawnMovement = new BlackPawnMovement();

    private BlackPawnMovement()
    {
    }

    public static BlackPawnMovement getInstance()
    {
        return blackPawnMovement;
    }

    @Override
    public List<Movement> getMovements()
    {
        return blackMovementList;
    }


    public static List<Movement> attackMovements()
    {
        return blackAttackList;
    }
}

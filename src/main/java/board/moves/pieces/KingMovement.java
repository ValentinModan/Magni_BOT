package board.moves.pieces;

import board.moves.Movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static board.moves.Movement.*;

public class KingMovement implements PieceMovement
{

    private static final KingMovement kingMovement = new KingMovement();

    private KingMovement()
    {
    }

    public static KingMovement getInstance()
    {
        return kingMovement;
    }

    private static final List<Movement> kingPieceMovement = new ArrayList<>(Arrays.asList(
            UP_LEFT,
            LEFT,
            LEFT_DOWN,
            DOWN,
            DOWN_RIGHT,
            RIGHT,
            UP_RIGHT,
            UP));

    @Override
    public List<Movement> getMovements()
    {
        return kingPieceMovement;
    }


}

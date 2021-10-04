package board.moves.pieces;

import board.moves.Movement;
import board.piecees.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueenMovement implements PieceMovement
{

    private static final List<Movement> queenMovementList =
            new ArrayList<>(Arrays.asList(
                    Movement.UP,
                    Movement.UP_LEFT,
                    Movement.LEFT,
                    Movement.LEFT_DOWN,
                    Movement.DOWN,
                    Movement.DOWN_RIGHT,
                    Movement.RIGHT,
                    Movement.UP_RIGHT));

    @Override
    public List<Movement> getMovements(Piece piece)
    {
        return queenMovementList;
    }
}

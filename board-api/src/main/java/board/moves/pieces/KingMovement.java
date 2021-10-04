package board.moves.pieces;

import board.moves.Movement;
import board.piecees.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KingMovement implements PieceMovement {

    private static final List<Movement> kingPieceMovement = new ArrayList<>(Arrays.asList(
            Movement.UP_LEFT,
            Movement.LEFT,
            Movement.LEFT_DOWN,
            Movement.DOWN,
            Movement.DOWN_RIGHT,
            Movement.RIGHT,
            Movement.UP_RIGHT,
            Movement.UP));


    @Override
    public List<Movement> getMovements(Piece piece) {
        return kingPieceMovement;
    }


}

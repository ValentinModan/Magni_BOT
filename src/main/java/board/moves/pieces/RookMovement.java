package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RookMovement implements PieceMovement{

    private static final List<Movement> rookMovementList = new ArrayList<>(Arrays.asList(
            Movement.UP,
            Movement.LEFT,
            Movement.DOWN,
            Movement.RIGHT
    ));

    private static final RookMovement rookMovement = new RookMovement();

    private RookMovement()
    {
    }

    public static RookMovement getInstance() {
        return rookMovement;
    }

    @Override
    public List<Movement> getMovements(Piece piece) {
        return rookMovementList;
    }
}

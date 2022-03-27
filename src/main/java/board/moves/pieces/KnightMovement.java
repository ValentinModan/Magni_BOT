package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnightMovement implements PieceMovement{

    private static final List<Movement>  knightsPossibleMovements = new ArrayList<>(Arrays.asList(
            Movement.KNIGHT_UP_LEFT,
            Movement.KNIGHT_LEFT_UP,
            Movement.KNIGHT_LEFT_DOWN,
            Movement.KNIGHT_DOWN_LEFT,
            Movement.KNIGHT_DOWN_RIGHT,
            Movement.KNIGHT_RIGHT_DOWN,
            Movement.KNIGHT_RIGHT_UP,
            Movement.KNIGHT_UP_RIGHT));

    private static final KnightMovement knightMovement = new KnightMovement();

    private KnightMovement()
    {
    }

    public static KnightMovement getInstance(){
        return knightMovement;
    }

    @Override
    public List<Movement> getMovements(Piece piece) {
        return knightsPossibleMovements;
    }


}

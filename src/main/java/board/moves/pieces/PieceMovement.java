package board.moves.pieces;

import board.moves.Movement;

import java.util.List;

@FunctionalInterface
public interface PieceMovement
{
    List<Movement> getMovements();
}

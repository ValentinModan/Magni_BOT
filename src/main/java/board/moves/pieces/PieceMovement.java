package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;

import java.util.List;

@FunctionalInterface
public interface PieceMovement
{

    List<Movement> getMovements(Piece piece);
}

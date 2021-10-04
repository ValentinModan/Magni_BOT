package board.moves.pieces;

import board.moves.Movement;
import board.piecees.Piece;

import java.util.List;

public interface PieceMovement
{

    List<Movement> getMovements(Piece piece);
}

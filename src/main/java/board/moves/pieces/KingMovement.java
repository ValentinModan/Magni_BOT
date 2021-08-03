package board.moves.pieces;

import board.moves.Movement;
import board.pieces.EmptyPiece;
import board.pieces.Piece;

import java.util.List;

public class KingMovement implements PieceMovement {

    private static final List<Movement> kingPieceMovement = MovementCalculator.getPossibleMoves(EmptyPiece.KING);

    @Override
    public List<Movement> getMovements(Piece piece) {
        return null;
    }


}

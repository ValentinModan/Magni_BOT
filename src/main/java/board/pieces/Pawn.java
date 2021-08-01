package board.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public String toString() {
        return isWhite() ? "♟" : "♙";
    }
}

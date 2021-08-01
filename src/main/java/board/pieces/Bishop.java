package board.pieces;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public String toString() {
        return isWhite() ? "♗" : "♗";
    }
}

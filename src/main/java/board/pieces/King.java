package board.pieces;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public String toString() {
        return isWhite() ? "♔" : "♚";
    }
}

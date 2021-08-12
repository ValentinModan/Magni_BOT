package board.pieces;

public class Knight extends Piece {


    public Knight(boolean isWhite) {
        super(isWhite, PieceType.KNIGHT);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public String toString() {
        return isWhite() ? "♘" : "♞";
    }
}

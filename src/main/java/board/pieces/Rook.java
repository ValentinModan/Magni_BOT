package board.pieces;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public String toString() {
        return isWhite() ? "♖" : "♜";
    }
}

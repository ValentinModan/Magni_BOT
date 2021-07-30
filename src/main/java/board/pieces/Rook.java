package board.pieces;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean isRook()
    {
        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "♖" : "♜";
    }
}

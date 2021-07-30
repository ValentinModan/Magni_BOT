package board.pieces;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean isBishop()
    {
        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "♗" : "♗";
    }
}

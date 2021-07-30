package board.pieces;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean isKing()
    {
        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "♔" : "♚";
    }
}

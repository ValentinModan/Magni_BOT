package board.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }


    @Override
    public boolean isPawn()
    {
        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "♟" : "♙";
    }
}

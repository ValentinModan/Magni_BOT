package board.pieces;

public class Knight extends Piece {


    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isKnight()
    {
        return true;
    }

    @Override
    public String toString() {
        return isWhite() ? "♘" : "♞";
    }
}

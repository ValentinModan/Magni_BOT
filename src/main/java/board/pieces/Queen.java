package board.pieces;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isQueen()
    {
        return true;
    }

    @Override
    public String toString() {
        return "♕";
    }
}

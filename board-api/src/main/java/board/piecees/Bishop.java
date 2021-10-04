package board.piecees;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public int getScore()
    {
        return 3;
    }

    @Override
    public String toString() {
        return isWhite() ? "♗" : "♗";
    }
}

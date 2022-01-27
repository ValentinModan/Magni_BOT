package board.pieces;

public class Bishop extends Piece {

    private final int BISHOP_SCORE = 3;

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
        return BISHOP_SCORE;
    }

    @Override
    public String toFen()
    {
        return isWhite() ? "B" : "b";
    }

    @Override
    public String toString() {
        return isWhite() ? "♗" : "♗";
    }
}

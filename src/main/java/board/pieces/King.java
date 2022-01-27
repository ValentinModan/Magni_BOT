package board.pieces;

public class King extends Piece {

    private final int KING_SCORE = 100;

    public King(boolean isWhite) {
        super(isWhite);
    }

    private boolean moved = false;

    public boolean hasMoved()
    {
        return moved;
    }

    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public int getScore()
    {
        return KING_SCORE;
    }

    @Override
    public String toFen()
    {
        return isWhite() ? "K" : "k";
    }

    @Override
    public String toString() {
        return isWhite() ? "♔" : "♚";
    }
}

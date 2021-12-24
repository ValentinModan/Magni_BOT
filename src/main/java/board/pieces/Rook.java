package board.pieces;

public class Rook extends Piece {
    private boolean hasMoved = false;

    private final int ROOK_SCORE = 5;

    public Rook(boolean isWhite) {
        super(isWhite);
    }


    public boolean isHasMoved()
    {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }

    @Override
    public int getScore()
    {
        return ROOK_SCORE;
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

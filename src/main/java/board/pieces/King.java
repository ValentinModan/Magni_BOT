package board.pieces;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    private boolean hasMoved = false;

    public boolean isHasMoved()
    {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public String toString() {
        return isWhite() ? "♔" : "♚";
    }
}

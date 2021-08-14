package board.pieces;

public class King extends Piece {

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
    public String toString() {
        return isWhite() ? "♔" : "♚";
    }
}

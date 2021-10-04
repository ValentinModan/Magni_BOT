package board.piecees;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
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
    public int getScore()
    {
        return 5;
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

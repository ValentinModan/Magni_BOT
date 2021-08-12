package board.pieces;

public class Pawn extends Piece {

    private boolean hasMoved = false;

    public Pawn()
    {
        super(true);
    }

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public boolean hasMoved()
    {
        return hasMoved;
    }

    public void moved()
    {
        hasMoved = true;
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public String toString() {
        return isWhite() ? "♟" : "♙";
    }
}

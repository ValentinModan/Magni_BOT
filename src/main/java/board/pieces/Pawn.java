package board.pieces;

public class Pawn extends Piece {

    private final int PAWN_SCORE = 1;

    public Pawn()
    {
        super(true);
    }

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public int getScore()
    {
        return PAWN_SCORE;
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

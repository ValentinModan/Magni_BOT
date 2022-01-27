package board.pieces;

public class Queen extends Piece {

    private final int QUEEN_SCORE = 9;

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }


    @Override
    public int getScore()
    {
        return QUEEN_SCORE;
    }

    @Override
    public String toFen()
    {
        return isWhite() ? "Q" : "q";
    }

    //todo add queen codecharacter
    @Override
    public String toString() {
        return "♕";
    }
}

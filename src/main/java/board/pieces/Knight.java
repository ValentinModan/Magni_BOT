package board.pieces;

public class Knight extends Piece
{
    private final int KNIGHT_SCORE = 3;

    public Knight(boolean isWhite)
    {
        super(isWhite, PieceType.KNIGHT);
    }

    @Override
    public PieceType getPieceType()
    {
        return PieceType.KNIGHT;
    }


    @Override
    public int getScore()
    {
        return KNIGHT_SCORE;
    }

    @Override
    public String toFen()
    {
        return isWhite() ? "N" : "n";
    }

    @Override
    public String toString()
    {
        return isWhite() ? "♘" : "♞";
    }
}

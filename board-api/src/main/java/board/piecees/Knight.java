package board.piecees;

public class Knight extends Piece
{


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
        return 3;
    }

    @Override
    public String toString()
    {
        return isWhite() ? "♘" : "♞";
    }
}

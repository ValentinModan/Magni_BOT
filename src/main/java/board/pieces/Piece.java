package board.pieces;

import java.util.Objects;

public abstract class Piece
{
    private final boolean isWhite;

    public Piece(boolean isWhite)
    {
        this.isWhite = isWhite;
    }

    public boolean isWhite()
    {
        return isWhite;
    }

    abstract public PieceType getPieceType();

    public int getScore()
    {
        return 0;
    }

    public boolean isOpponentOf(Piece piece)
    {
        if (piece == null) {
            return false;
        }
        return this.isWhite != piece.isWhite;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return isWhite == piece.isWhite;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(isWhite);
    }

    public String toFen()
    {
        return " ";
    }

    @Override
    public String toString()
    {
        return ".";
    }
}

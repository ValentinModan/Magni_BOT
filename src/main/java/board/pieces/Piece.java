package board.pieces;

import java.util.Objects;

public abstract class Piece {

    private boolean isWhite;
    private char displayLetter = '.';
    private final int score = 0;

    private PieceType pieceType;


    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Piece(boolean isWhite,PieceType pieceType)
    {
        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return isWhite == piece.isWhite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWhite);
    }

    @Override
    public String toString() {
        return ".";
    }
}

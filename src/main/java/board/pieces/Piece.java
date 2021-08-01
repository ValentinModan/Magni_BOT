package board.pieces;

public abstract class Piece {

    private boolean isWhite;
    private char displayLetter = '.';

    private PieceType pieceType;


    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
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

    @Override
    public String toString() {
        return ".";
    }
}

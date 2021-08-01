package board.pieces;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public String toString() {
        return "♕";
    }
}

package board.piecees;

public class Queen extends Piece {

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
        return 9;
    }

    @Override
    public String toString() {
        return "♕";
    }
}

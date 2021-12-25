package board.pieces;

//TODO: find better name for this class or/and use enum instead
public class EmptyPiece {

    public static final Pawn WHITE_PAWN= new Pawn(true);
    public static final Pawn BLACK_PAWN = new Pawn(false);
    public static final Bishop BISHOP= new Bishop(false);
    public static final Knight KNIGHT= new Knight(false);
    public static final Rook ROOK= new Rook(false);
    public static final Queen QUEEN= new Queen(false);
    public static final King KING= new King(false);
}

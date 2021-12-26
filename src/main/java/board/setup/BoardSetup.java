package board.setup;

import board.Board;
import board.Position;
import board.pieces.*;

//TODO: replace positions with enums (not worth the time)
public class BoardSetup {

    private static final boolean WHITE = true;
    private static final boolean BLACK = false;

    public static void setupBoard(Board board) {
        setPieces(board);
    }

    private static void setPieces(Board board) {
        addPawns(board);
        addKnights(board);
        addBishops(board);
        addRooks(board);
        addQueens(board);
        addKings(board);
    }

    private static void addPawns(Board board)
    {
        for(char letter = 'a';letter<='h';letter++)
        {
            board.addPiece(new Position(letter,2), new Pawn(WHITE));
            board.addPiece(new Position(letter,7), new Pawn(BLACK));
        }
    }

    private static void addKnights(Board board)
    {
        board.addPiece(new Position('b',1),new Knight(WHITE));
        board.addPiece(new Position('g',1),new Knight(WHITE));
        board.addPiece(new Position('b',8),new Knight(BLACK));
        board.addPiece(new Position('g',8),new Knight(BLACK));
    }

    private static void addBishops(Board board)
    {
        board.addPiece(new Position('c',1),new Bishop(WHITE));
        board.addPiece(new Position('f',1),new Bishop(WHITE));
        board.addPiece(new Position('c',8),new Bishop(BLACK));
        board.addPiece(new Position('f',8),new Bishop(BLACK));
    }

    private static void addRooks(Board board)
    {
        board.addPiece(new Position('a',1),new Rook(WHITE));
        board.addPiece(new Position('h',1),new Rook(WHITE));
        board.addPiece(new Position('a',8),new Rook(BLACK));
        board.addPiece(new Position('h',8),new Rook(BLACK));
    }

    private static void addQueens(Board board)
    {
        board.addPiece(new Position('d',1), new Queen(WHITE));
        board.addPiece(new Position('d',8), new Queen(BLACK));
    }

    private static void addKings(Board board)
    {
        board.addPiece(new Position('e',1),new King(WHITE));
        board.addPiece(new Position('e',8),new King(BLACK));
    }
}

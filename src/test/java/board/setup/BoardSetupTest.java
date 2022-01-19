package board.setup;

import board.Board;
import board.PositionEnum;
import board.pieces.PieceType;
import org.junit.jupiter.api.Test;

import static board.PositionEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardSetupTest
{
    @Test
    void fenNotationKingPositions1() throws Exception
    {
        Board board = new Board();
        String fenNotationString = "r3r1k1/pp1n2pp/3Bpp2/n7/3N4/5Q2/Pq4PP/4RRK1 b - - 1 23";
        BoardSetup.fenNotationBoardSetup(board, fenNotationString);
        System.out.println(board);

        assertEquals(PieceType.KING, board.getPieceAt(G8.getPosition()).getPieceType());
        assertEquals(PieceType.KING, board.getPieceAt(G1.getPosition()).getPieceType());

        assertFalse(board.isWhiteToMove());
    }

    @Test
    void fenNotationKingPositions2() throws Exception
    {
        Board board = new Board();
        String fenNotationString = "r1bqkb1r/2ppnpp1/p6p/np2p3/4P3/1BN2N2/PPPP1PPP/R1BQ1RK1 w kq - 2 8";
        BoardSetup.fenNotationBoardSetup(board, fenNotationString);
        System.out.println(board);

        assertEquals(PieceType.KING, board.getPieceAt(PositionEnum.E8.getPosition()).getPieceType());
        assertEquals(PieceType.KING, board.getPieceAt(G1.getPosition()).getPieceType());

        assertTrue(board.isWhiteToMove());
    }

    @Test
    void fenNotationQueens1() throws Exception
    {
        Board board = new Board();
        String fenNotation = "4r1k1/5b2/5qp1/1p5p/1Q3P1P/6P1/6BK/R7 b - - 6 39";
        BoardSetup.fenNotationBoardSetup(board, fenNotation);
        assertEquals(PieceType.QUEEN, board.getPieceAt(B4.getPosition()).getPieceType());
        assertEquals(PieceType.QUEEN, board.getPieceAt(F6.getPosition()).getPieceType());

        System.out.println(board);

        assertFalse(board.isWhiteToMove());

    }
}
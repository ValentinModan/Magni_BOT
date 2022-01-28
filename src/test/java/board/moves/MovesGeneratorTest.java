package board.moves;

import board.Board;
import helper.MovesGenerator;
import board.Position;
import board.pieces.PieceType;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovesGeneratorTest
{

    Board board;


    @BeforeEach
    public void setUp()
    {
        board = new Board();
        BoardSetup.setupBoard(board);
    }

    @Test
    void makeMoves()
    {
        MovesGenerator.makeMoves(board, "b2b4 b7b5");

        assertSame(board.getMovingPiece(new Position('b', 4)).getPieceType(), PieceType.PAWN);
        assertSame(board.getTakenPiecesMap().get(new Position('b', 5)).getPieceType(), PieceType.PAWN);


    }
}
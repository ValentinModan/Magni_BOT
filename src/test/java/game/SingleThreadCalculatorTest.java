package game;

import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleThreadCalculatorTest
{
    Board board;
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();


    @BeforeEach
    public void setUp()
    {
        board = new Board();
        BoardSetup.setupBoard(board);
        GameBoard.actualBoard = board;
    }

    @Test
    void bestResponse() throws Exception
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 a2a3";
        MovesGenerator.makeMoves(board, firstMoves);
        Move bestMove = singleThreadCalculator.bestResponse(board);

        assertEquals(MoveConvertor.stringToMove("h4e1"), bestMove);

    }
}
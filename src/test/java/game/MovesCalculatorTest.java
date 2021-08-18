package game;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovesCalculatorTest
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
    }

    @Test
    void calculateDepth1()
    {
        String move = "d2d4 d7d5 d1d2 b7b6 d2h6";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 10, 2);

        assertEquals(move1.getInitialPosition(), new Position('g', 7));
        assertEquals(move1.getFinalPosition(), new Position('h', 6));
    }

    @Test
    void calculateDepth1_2()
    {
        String move = "c2c3 e7e6 b1a3 f8d6 b2b3 d6a3";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 10, 2);

        assertEquals(move1.getInitialPosition(), new Position('c', 1));
        assertEquals(move1.getFinalPosition(), new Position('a', 3));
    }

    @Test
    void calculateDepth1_3_faling_test_to_investigate()
    {
        String move = "c2c3 e7e6 b1a3 g8f6 a1b1 f8c5 b1a1 b8c6 b2b3 c5a3";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 6, 8);

        assertEquals(new Position('c', 1), move1.getInitialPosition());
        assertEquals( new Position('a', 3),move1.getFinalPosition());
    }

    @Test
    void calculateDepth1_3_faling_test_to_()
    {
        String move = "c2c3 e7e6 b1a3 g8f6 a1b1 f8c5 b1a1 b8c6 b2b3 c5a3";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 6, 6);

        assertEquals(new Position('c', 1), move1.getInitialPosition());
        assertEquals( new Position('a', 3),move1.getFinalPosition());
    }
}
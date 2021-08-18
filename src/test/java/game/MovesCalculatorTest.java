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
}
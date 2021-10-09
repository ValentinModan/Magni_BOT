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
    void calculateDepth1_3_failing_test_to_investigate()
    {
        String move = "c2c3 e7e6 b1a3 g8f6 a1b1 f8c5 b1a1 b8c6 b2b3 c5a3";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 6, 8);

        assertEquals(new Position('c', 1), move1.getInitialPosition());
        assertEquals( new Position('a', 3),move1.getFinalPosition());
    }

    @Test
    void calculateDepth1_3_failing_test_to_()
    {
        String move = "c2c3 e7e6 b1a3 g8f6 a1b1 f8c5 b1a1 b8c6 b2b3 c5a3";

        MovesGenerator.makeMoves(optimizedBoard, move);

        optimizedBoard.computePossibleMoves();

        Move move1 = MovesCalculator.calculate(optimizedBoard, 6, 6);

        assertEquals(new Position('c', 1), move1.getInitialPosition());
        assertEquals( new Position('a', 3),move1.getFinalPosition());
    }

    @Test
    void computeRandomMoves()
    {
        String moves = "c2c3 e7e5 b1a3";

        MovesGenerator.makeMoves(optimizedBoard,moves);
        optimizedBoard.computePossibleMoves();
        Move bestMove = MovesCalculator.calculate2(optimizedBoard,1,7);


        System.out.println(bestMove);
    }

    @Test
    void randomTest2()
    {
        try {
            String moves = "c2c3 e7e6 b1a3 f8a3 b2a3 b8c6";

            MovesGenerator.makeMoves(optimizedBoard, moves);
            Move bestMove = MovesCalculator.calculate(optimizedBoard, 6, 6);
            System.out.println(bestMove);
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.out.println(optimizedBoard.allMoves);
        }

        System.out.println("success");
    }

    @Test
    void randomTest3()
    {
        String moves = "c2c3 e7e6 b1a3 g8f6 a1b1 f8a3";
        MovesGenerator.makeMoves(optimizedBoard,moves);
        Move bestMove = MovesCalculator.calculate2(optimizedBoard, 2, 3);
        System.out.println(bestMove);
    }

    @Test
    void randomTest4()
    {
        String moves = "c2c3 e7e6 b1a3 d8f6";
        MovesGenerator.makeMoves(optimizedBoard,moves);
        Move bestMove = MovesCalculator.calculate2(optimizedBoard, 10, 1);
        System.out.println(bestMove);
    }

    @Test
    void randomTestDepth1Check()
    {
        String moves = "c2c3 e7e6 b1a3 g8f6 a1b1 b8c6 b1a1 f8a3 b2a3 d7d5 c3c4 d5c4 a1b1 d8d5 b1b7 c8b7 a3a4 f6e4 a2a3 d5f5 a4a5 e8h8 a3a4 f8d8 a5a6 d8d2 d1d2 e4d2 a6b7 a8b8 e1d2 f5g5";
        MovesGenerator.makeMoves(optimizedBoard,moves);
        Move bestMove = MovesCalculator.calculate(optimizedBoard, 40, 1);
        System.out.println(bestMove);
    }

    @Test
    void randomTestRandomDepth()
    {
        String moves = "c2c3 f7f6 b1a3 e7e5 a1b1 f8a3 b2a3 d7d6 b1b2 c8f5 b2b7 g8h6";
        MovesGenerator.makeMoves(optimizedBoard,moves);
        Move bestMove = MovesCalculator.calculate(optimizedBoard, 10, 1);
        System.out.println(bestMove);
    }


    @Test
    void randomTestRandomDepth1()
    {
        String moves = "d2d4 h7h5 c2c4 h8h7 b1c3 h7h8 d1d3 g7g6 g1f3 h8h7 h2h3 h7h8 g2g4 h5g4 h3g4 h8h1 c1f4";
        MovesGenerator.makeMoves(optimizedBoard,moves);
        Move bestMove = MovesCalculator.calculate(optimizedBoard, 40, 1);
        System.out.println(bestMove);
    }
}
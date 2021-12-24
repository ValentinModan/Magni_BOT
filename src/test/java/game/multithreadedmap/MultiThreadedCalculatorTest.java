package game.multithreadedmap;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import game.CleanMoveCalculator;
import game.GameBoard;
import mapmovement.MovementMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiThreadedCalculatorTest
{

    @Test
    void possibleMoves() throws CloneNotSupportedException, InterruptedException
    {
        OptimizedBoard optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);

        MultiThreadedCalculator multiThreadedCalculator = new MultiThreadedCalculator();
        multiThreadedCalculator.possibleMoves(optimizedBoard);


    }

    @Test
    void mateIn1() throws InterruptedException, CloneNotSupportedException
    {
        String moves = "d2d4 g8f6 c2c4 e7e6 b1c3 f8b4 g1f3 d7d5 c4c5 e6e5 a2a3 b4c3 b2c3 e5d4 c3d4 a7a6 e2e3 c7c6 f1d3 b7b6 e1h1 b6c5 d4c5 h7h6 d1b3 a6a5 c1b2 h6h5 a1b1 h5h4 b2c3 a5a4 c3f6 a4b3 b1b3 g7f6 d3f5 c8f5 f1b1 f5b1 b3b1 a8a3 f3d4 a3e3 b1b8";
        OptimizedBoard optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
        MovesGenerator.makeMoves(optimizedBoard, moves);

        GameBoard.actualBoard = optimizedBoard;


        MultiThreadedCalculator multiThreadedCalculator = new MultiThreadedCalculator();

        Move bestMove = multiThreadedCalculator.possibleMoves(optimizedBoard);


        System.out.println(optimizedBoard);

        assertEquals(MoveConvertor.stringToMove("e3e1"),bestMove);
    }
}
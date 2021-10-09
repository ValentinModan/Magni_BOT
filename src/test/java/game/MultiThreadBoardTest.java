package game;

import board.OptimizedBoard;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiThreadBoardTest
{

    @Test
    void compute() throws CloneNotSupportedException
    {
        OptimizedBoard optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);

        MultiThreadBoard multiThreadBoard = new MultiThreadBoard();

        multiThreadBoard.compute(optimizedBoard);

        MovesGenerator.makeMoves(optimizedBoard,"d2d4");

        multiThreadBoard.compute(optimizedBoard);
    }
}
package game.mates;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import game.MovesCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MateIn1
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUP()
    {
        optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
    }

    @Test
    public void scholarsMate_depth_search_1()
    {
        String firstMoves = "f2f3 e7e6 g2g4 d8h4 a2a3";
        MovesGenerator.makeMoves(optimizedBoard, firstMoves);

        Move bestCalculateMove = MovesCalculator.calculate(optimizedBoard, 10, 2);

        Move expectedMove = MoveConvertor.stringToMove("h4e1");
        Assertions.assertEquals(expectedMove, bestCalculateMove);
    }
}

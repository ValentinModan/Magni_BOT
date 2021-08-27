package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CleanMovesCalculatorTest
{
    OptimizedBoard optimizedBoard;

    @BeforeEach
    public void setUp()
    {
        optimizedBoard = new OptimizedBoard();
        BoardSetup.setupBoard(optimizedBoard);
    }

    @Test
    public void notLoseHorsey()
    {
        String moves = "b2b4 b8c6 d2d4 d7d6 c2c3";
        MovesGenerator.makeMoves(optimizedBoard, moves);
        optimizedBoard.setPossibleMoves(null);
        assert optimizedBoard.getPossibleMoves() == null;
        Move bestMove = CleanMoveCalculator.calculate2(optimizedBoard, 5);

        for(Move move: optimizedBoard.getPossibleMoves())
        {
            System.out.printf("%5d:    ",move.moveScore());
            System.out.println(move.moveWithBestResponse());
        }


    }
}

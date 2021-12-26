package game;

import board.Board;
import board.moves.Move;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CleanMovesCalculatorTest
{
    Board board;

    @BeforeEach
    public void setUp()
    {
        board = new Board();
        BoardSetup.setupBoard(board);
    }

    @Test
    public void notLoseHorsey()
    {
        String moves = "b2b4 b8c6 d2d4 d7d6 c2c3";
        MovesGenerator.makeMoves(board, moves);
        board.setPossibleMoves(null);
        assert board.getPossibleMoves() == null;
        Move bestMove = CleanMoveCalculator.calculate2(board, 5);

        for(Move move: board.getPossibleMoves())
        {
            System.out.printf("%5d:    ",move.moveScore());
            System.out.println(move.moveWithBestResponse());
        }


    }
}

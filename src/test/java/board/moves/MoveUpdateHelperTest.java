package board.moves;

import board.Board;
import board.setup.BoardSetup;
import helper.MovesGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveUpdateHelperTest
{


    @Test
    public void test()
    {
        Board board = new Board();
        BoardSetup.setupBoard(board);
        MovesGenerator.makeMoves(board, "e2e4 e7e5 g1f3 b8c6 f1b5 g8f6 e1g1");
        System.out.println(board);
    }

}
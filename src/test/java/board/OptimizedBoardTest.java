package board;

import board.setup.BoardSetup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptimizedBoardTest {

    OptimizedBoard board = new OptimizedBoard();
    @Test
    void moveThenUndoMove() {

        BoardSetup.setupBoard(board);

        System.out.println(board);

    }
}
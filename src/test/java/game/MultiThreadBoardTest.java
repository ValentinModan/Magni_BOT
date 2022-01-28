package game;

import board.Board;
import helper.MovesGenerator;
import board.setup.BoardSetup;
import toDelete.MultiThreadBoard;

class MultiThreadBoardTest
{

    void compute() throws CloneNotSupportedException
    {
        Board board = new Board();
        BoardSetup.setupBoard(board);

        MultiThreadBoard multiThreadBoard = new MultiThreadBoard();

        multiThreadBoard.compute(board);

        MovesGenerator.makeMoves(board, "d2d4");

        multiThreadBoard.compute(board);
    }
}
package game;

import board.Board;
import board.moves.MovesGenerator;
import board.setup.BoardSetup;

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
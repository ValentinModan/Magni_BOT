package game;

import board.Board;
import board.moves.results.Endgame;
import board.setup.BoardSetup;
import game.kingcheck.attacked.KingSafety;
import reader.ConsoleReader;

import javax.swing.*;

public class GameBoard {
    boolean isWhiteToPlay = true;

    Board actualBoard;

    public GameBoard() {
        actualBoard = new Board();
        BoardSetup.setupBoard(actualBoard);
    }

    public void startGame()
    {
        System.out.println(actualBoard);
        while(true) {
            if (isWhiteToPlay) {
                String move = ConsoleReader.readMove();

                actualBoard.movePiece(move);

                if(KingSafety.getNumberOfAttackers(actualBoard,false)>0)
                {
                    System.out.println("Check!");
                }

            } else {
                String move = ConsoleReader.readMove();

                actualBoard.movePiece(move);
                if(KingSafety.getNumberOfAttackers(actualBoard,true)>0)
                {
                    System.out.println("Check!");
                }
            }

            System.out.println(actualBoard);

        }
    }
}

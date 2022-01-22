import board.Board;
import game.GameBoard;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, InterruptedException
    {
        configureOutputFileForLogging();

        while (true) {
            GameBoard gameBoard = new GameBoard();
            for (int i = 1; i <= 10; i++) {
                try {
                    //  gameBoard.waitForChallengeAndAcceptIt();
                    gameBoard.challengePlayer("maia1");

                } catch(Exception e) {
                    e.printStackTrace();

                }
                Thread.sleep(60000);
            }


        }

        //  }
    }


    private static void configureOutputFileForLogging() throws FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

    }

}
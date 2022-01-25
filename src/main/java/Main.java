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

        GameBoard gameBoard = new GameBoard();
        try {
            //  gameBoard.waitForChallengeAndAcceptIt();
            gameBoard.challengePlayer("sargon-4ply");

        } catch (Exception e) {
            e.printStackTrace();

        }
        Thread.sleep(60000);
    }


    private static void configureOutputFileForLogging() throws FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

    }

}
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
//        PossibleMovesCalculator1 possibleMovesCalculator1;

        GameBoard gameBoard = new GameBoard();
        try {
            //  gameBoard.waitForChallengeAndAcceptIt();
            gameBoard.challengePlayer("sargon-2ply", 1200, 20, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void configureOutputFileForLogging() throws FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

    }

}
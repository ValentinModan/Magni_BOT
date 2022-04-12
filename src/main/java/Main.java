import board.Board;
import game.GameBoard;
import game.GameConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, InterruptedException
    {/// runApp();
        GameConfig.config();


    }
    private static void runApp() throws FileNotFoundException
    {
        configureOutputFileForLogging();
//        PossibleMovesCalculator1 possibleMovesCalculator1;

        GameBoard gameBoard = new GameBoard();
        try {
            //  gameBoard.waitForChallengeAndAcceptIt();
            gameBoard.challengePlayer("sargon-1ply", 600, 30, false);

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
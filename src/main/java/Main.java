import board.Board;
import game.GameBoard;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException, InterruptedException
    {

        Testing.test(null);
//        configureOutputFileForLogging();
////        PossibleMovesCalculator1 possibleMovesCalculator1;
//
//        GameBoard gameBoard = new GameBoard();
//        try {
//            //  gameBoard.waitForChallengeAndAcceptIt();
//            gameBoard.challengePlayer("turochamp-1ply", 600, 30, true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    private static void configureOutputFileForLogging() throws FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

    }

}
import api.Dog;
import board.Board;
import game.GameBoard;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {

        configureOutputFileForLogging();
        Dog dog = new Dog();
        dog.hello();
//       // while(true) {
        GameBoard gameBoard = new GameBoard();

        try {
            gameBoard.startPlayerGame();
        } catch (Exception e) {
            e.printStackTrace();
            Board.displayAllMoves();
        }

        //  }
    }


    private static void configureOutputFileForLogging() throws FileNotFoundException
    {
        PrintStream out = new PrintStream(new FileOutputStream("logs.log"));
        System.setOut(out);

    }

}
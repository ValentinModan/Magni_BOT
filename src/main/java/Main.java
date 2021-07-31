import board.Board;
import board.setup.BoardSetup;
import game.GameBoard;

public class Main {

    public static void main(String[] args) {


        System.out.println("Testing the responses");

        GameBoard gameBoard = new GameBoard();

        try {


            gameBoard.startGame();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}

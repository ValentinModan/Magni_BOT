import api.Controller;
import board.Board;
import board.setup.BoardSetup;
import game.GameBoard;

public class Main {

    public static void main(String[] args) {


        System.out.println("Testing the responses");

        GameBoard gameBoard = new GameBoard();

        try {

            Controller controller  = new Controller();

            //controller.sendRestTemplateRequest();

            gameBoard.startGame();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}

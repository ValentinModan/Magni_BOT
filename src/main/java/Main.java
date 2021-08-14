import api.Controller;
import api.challenges.Challenge;
import board.Board;
import board.setup.BoardSetup;
import game.GameBoard;

public class Main {

    public static void main(String[] args) {


        try {
            System.out.println("Testing the responses");

            GameBoard gameBoard = new GameBoard();


            Controller controller = new Controller();

            // controller.sendRestTemplateRequest();

            //gameBoard.startGame();

           Challenge.getChallenges();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

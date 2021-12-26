import board.Board;
import game.GameBoard;

public class Main
{

    public static void main(String[] args)
    {
        while(true) {
            System.out.println("Waiting for a new challenge!");
            GameBoard gameBoard = new GameBoard();

            try {
                gameBoard.startPlayerGame();
            } catch (Exception e) {
                e.printStackTrace();
                Board.displayAllMoves();
                System.out.println(" ");
            }

        }
    }
}
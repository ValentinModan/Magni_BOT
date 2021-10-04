import board.OptimizedBoard;
import game.GameBoard;

public class Main
{

    public static void main(String[] args)
    {
        GameBoard gameBoard = new GameBoard();

        try {
            gameBoard.startPlayerGame();
        } catch (Exception e) {
            e.printStackTrace();
            OptimizedBoard.displayAllMoves();
            System.out.println(" ");
        }
    }
}
import board.OptimizedBoard;
import game.GameBoard;
import openings.Opening;
import openings.OpeningController;
import openings.OpeningReader;

public class Main
{

    static String[] whiteMoves = {"d2d4", "a2a3", "b2b4"};
    static String[] blackMoves = {"a7a5", "b7b5", "c7c5"};

    public static void main(String[] args)
    {
        GameBoard gameBoard = new GameBoard();

        try {
            gameBoard.startPlayerGame();
        } catch (Exception e) {
            OptimizedBoard.displayAllMoves();
            System.out.println(" ");
            System.out.println(OptimizedBoard.allMoves);
            e.printStackTrace();
        }
    }
}
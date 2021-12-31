import board.Board;
import board.moves.Move;
import board.moves.MoveConvertor;
import board.setup.BoardSetup;
import game.GameBoard;
import game.multithreadedmap.MultiThreadedCalculator;

public class Main
{

    public static void main(String[] args) throws CloneNotSupportedException, InterruptedException
    {


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


}
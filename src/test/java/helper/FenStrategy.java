package helper;

import board.Board;
import board.moves.movetypes.Move;
import game.GameBoard;
import game.SingleThreadCalculator;

public class FenStrategy
{
    private static String strategy = "singleThread";
    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();

    public static Move bestResponse(Board board)
    {
        try {
            switch (strategy) {
                case "singleThread":
                    SingleThreadCalculator singleThreadCalculator = new SingleThreadCalculator();
                    GameBoard.actualBoard = board;
                    return singleThreadCalculator.bestResponse(board);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}


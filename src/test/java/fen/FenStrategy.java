package fen;

import board.Board;
import board.moves.Move;
import toDelete.CleanMoveCalculator;
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
                case "other":
                    return CleanMoveCalculator.calculate2(board,2);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}


package board.moves;

import board.OptimizedBoard;

public class MovesGenerator
{

    public static void makeMoves(OptimizedBoard optimizedBoard, String moves)
    {
        String[] movesArray = moves.split(" ");
        for (String move : movesArray) {
            optimizedBoard.move(MoveConvertor.toMove(move));
            optimizedBoard.setWhiteToMove(!optimizedBoard.isWhiteToMove());
        }
    }
}

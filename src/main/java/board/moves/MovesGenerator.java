package board.moves;

import board.OptimizedBoard;

import static board.moves.MoveConvertor.stringToMove;

public class MovesGenerator
{

    //todo move generator to testing package
    public static void makeMoves(OptimizedBoard optimizedBoard, String moves)
    {
        String[] movesArray = moves.split(" ");
        for (String move : movesArray) {
            optimizedBoard.move(stringToMove(move));
            optimizedBoard.nextTurn();
        }
    }
}

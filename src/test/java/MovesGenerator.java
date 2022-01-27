import board.Board;

import static board.moves.MoveConvertor.stringToMove;

public class MovesGenerator
{
    //todo move generator to testing package
    public static void makeMoves(Board board, String moves)
    {
        String[] movesArray = moves.split(" ");
        for (String move : movesArray) {
            board.move(stringToMove(move));
            board.computePossibleMoves();
            board.nextTurn();
        }
    }
}

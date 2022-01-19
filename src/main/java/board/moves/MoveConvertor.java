package board.moves;

import board.Position;

public class MoveConvertor {

    public static Move stringToMove(String string)
    {
        if(string == null)
        {
            return null;
        }
        return new Move(new Position(string.charAt(0), string.charAt(1) - '0'),
                new Position(string.charAt(2), (int) string.charAt(3) - '0'));
    }

    public static Move positionsToMove(Position firstPosition, Position secondPosition)
    {
        return new Move(firstPosition,secondPosition);
    }
}

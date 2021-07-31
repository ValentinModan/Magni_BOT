package board.moves;

import board.Position;

public class MoveConvertor {

    public static Move toMove(String string)
    {
        return new Move(new Position(string.charAt(0), string.charAt(1) - '0'),
                new Position(string.charAt(2), (int) string.charAt(3) - '0'));
    }
}

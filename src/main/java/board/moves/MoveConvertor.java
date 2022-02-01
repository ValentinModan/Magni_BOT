package board.moves;

import board.Position;

public class MoveConvertor
{

    public static Move stringToMove(String string)
    {
        if (string == null) {
            return null;
        }
        Move move = new Move(new Position(string.charAt(0), string.charAt(1) - '0'),
                             new Position(string.charAt(2), (int) string.charAt(3) - '0'));

        if (string.length() > 4) {
            char promotion = string.charAt(4);
            if (promotion > 'A' && promotion < 'Z') {
                move.setPromotionSmithNotation(promotion);
            }
        }

        return move;
    }

    public static Move positionsToMove(Position firstPosition, Position secondPosition)
    {
        return new Move(firstPosition, secondPosition);
    }
}

package helper;

import board.MovementMap;

public class MovementMapCounter
{
    public static int countChildrenMoves(MovementMap movementMap)
    {
        int sum = 0;
        if (movementMap.getMovementMap().isEmpty()) {
            return 1;
        }

        for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
            sum += countChildrenMoves(movementMap1);
        }

        return sum;
    }
}

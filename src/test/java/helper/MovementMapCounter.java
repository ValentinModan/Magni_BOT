package helper;

import board.Board;
import board.MovementMap;
import board.moves.MoveUpdateHelper;
import game.GameBoardHelper;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MovementMapCounter
{
    public static Map<String, Integer> pieceMap = new HashMap<>();

    public static int countChildrenMovesWithDepth(MovementMap movementMap, int depth) throws CloneNotSupportedException
    {
        int sum = 0;

        if (depth == 0) {
            return 1;
        }
        if (depth > 0 && movementMap.getMovementMap() != null && !movementMap.getMovementMap().isEmpty()) {
            for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
                sum += countChildrenMovesWithDepth(movementMap1, depth - 1);
            }
        }

        return sum;
    }


    public static int countChildrenMoves(MovementMap movementMap) throws CloneNotSupportedException
    {
        int sum = 0;

        if (movementMap.getParent() != null) {
            Board parentBoard = movementMap.getParent().generateBoardForCurrentPosition();
            MoveUpdateHelper.moveUpdate(parentBoard, movementMap.getCurrentMove());
            if (movementMap.getCurrentMove().isEnPassant()) {
                en_passant++;
            }
            if (movementMap.getCurrentMove().getTakenPiece() != null) {
                captures++;
            }
        }

        if (movementMap.getMovementMap() == null) {
            return 1;
        }
        if (movementMap.getMovementMap().isEmpty()) {
            return 1;
        }

        for (MovementMap movementMap1 : movementMap.getMovementMap().values()) {
            sum += countChildrenMoves(movementMap1);
        }

        return sum;
    }

    public static void displayresults()
    {
        System.out.println("En passant mvoes " + en_passant);
        System.out.println("Capture moves" + captures);
        System.out.println("Checkmate moves +" + check_mates);
    }

    public static int en_passant = 0;
    public static int captures = 0;
    public static int check_mates = 0;

    public static void displayMoves(MovementMap movementMap, String string) throws CloneNotSupportedException
    {
        if (movementMap.getMovementMap().isEmpty()) {
            System.out.println(string.substring(4) + " " + movementMap.getCurrentMove());
        }

        for (MovementMap movementMap1 : movementMap.getMovementMap().values().stream().sorted(Comparator.comparing(MovementMap::toString)).collect(Collectors.toList())) {
            if (string.isEmpty()) {
                displayMoves(movementMap1, movementMap.getCurrentMove().toString());
            }
            else {
                displayMoves(movementMap1, string + " " + movementMap.getCurrentMove().toString());
            }
        }
    }
}

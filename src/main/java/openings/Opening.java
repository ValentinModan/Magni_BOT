package openings;

import java.util.Arrays;

public class Opening
{
    private final String moveName;
    private final String move;

    private static String movesUntilNow = "";

    public Opening(String openingString)
    {
        String[] array = openingString.split(",");
        moveName = array[0];
        move = array[1];
    }

    public static void updateMovesStringWithMove(String move)
    {
        if (movesUntilNow.isEmpty()) {
            movesUntilNow = move;
        }
        else {
            movesUntilNow = movesUntilNow + " " + move;
        }
    }

    public String getNextMove()
    {
        if (movesUntilNow.isEmpty()) {
            return move.substring(0, 4);
        }
        if (move.startsWith(movesUntilNow) && move.length() > movesUntilNow.length()) {
            int startingPosition = movesUntilNow.length() + 1;
            return move.substring(startingPosition, startingPosition + 4);
        }
        return null;
    }

    public boolean isThisOpening(String currentMove)
    {
        if (movesUntilNow.isEmpty()) {
            return isThisOpeningFromMoves(currentMove);
        }
        return isThisOpeningFromMoves(movesUntilNow + " " + currentMove);
    }

    private boolean isThisOpeningFromMoves(String moves)
    {
        return secondIsThisOpeningFromMoves(moves);
    }

    private boolean secondIsThisOpeningFromMoves(String moves)
    {
        try {
            String[] moveArray = move.substring(0, moves.length()).split(" ");
            Arrays.sort(moveArray);
            String[] movesArray = moves.split(" ");
            Arrays.sort(movesArray);
            return Arrays.equals(movesArray, moveArray);
        } catch (Exception e) {
            return false;
        }
    }

    private String extractExactMove(String firstMoves)
    {
        try {
            return move.substring(firstMoves.length()).split(" ")[0];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean hasContinuation()
    {
        return move.length() > movesUntilNow.length();
    }

    public String getMove()
    {
        return move;
    }

    @Override
    public String toString()
    {
        return "Opening{" +
                "moveName='" + moveName + '\'' +
                ", move='" + move + '\'' +
                '}';
    }
}

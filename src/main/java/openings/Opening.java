package openings;

import java.util.Arrays;

public class Opening
{
    private String moveName;
    private String move;

    public static String movesUntilNow = "";

    public Opening(String openingString) throws Exception
    {
        String[] array = openingString.split(",");
        moveName = array[0];
        move = array[1];
    }

    private String extractNextMove(String firstMoves)
    {
        try {
            if (movesUntilNow.equals("")) {
                return move.split(" ")[0];
            }
            String nextMove = move.substring(movesUntilNow.length() + 1).split(" ")[0];
            return nextMove;
        } catch (Exception e) {
            return null;
        }
    }

    public static void addMove(String move)
    {
        if (movesUntilNow.equals("")) {
            movesUntilNow = move;
        }
        else {
            movesUntilNow = movesUntilNow + " " + move;
        }
    }

    public String getNextMove()
    {
        if (movesUntilNow.equals("")) {
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
        if (movesUntilNow.equals("")) {
            return isThisOpeningFromMoves(currentMove);
        }
        return isThisOpeningFromMoves(movesUntilNow + " " + currentMove);
    }

    private boolean isThisOpeningFromMoves(String moves)
    {
        return secondIsThisOpeningFromMoves(moves);
        // return move.startsWith(moves);
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

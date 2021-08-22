package game.gameSetupOptions;

import board.moves.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOptions
{
    public static final int CHECK_MATE_SCORE = -1000;
    public static final int STALE_MATE_SCORE = -100;

    public static final int MINIMUM_MOVES = 10;
    public static final int MAXIMUM_MOVES = 20;

    public static Move checkMate()
    {
        return new Move(CHECK_MATE_SCORE);
    }

    public static Move staleMate()
    {
        return new Move(STALE_MATE_SCORE);
    }

    public static List<Move> extractMoves(List<Move> moveList, int currentDepth)
    {
        int movesPercentage = percentageFromDepth(currentDepth);
        if (movesPercentage < 80) {
            Collections.shuffle(moveList);
        }

        int movesNumber = Math.max(MINIMUM_MOVES, moveList.size() * movesPercentage / 100);

        movesNumber = Math.min(movesNumber, MAXIMUM_MOVES);

        movesNumber = Math.min(movesNumber, moveList.size());
        return new ArrayList<>(moveList.subList(0, movesNumber));
    }

    private static int percentageFromDepth(int currentDepth)
    {
        if (currentDepth <= 4) {
            return 100;
        }
        if (currentDepth <= 6) {
            return 50;
        }
        if (currentDepth <= 8) {
            return 40;
        }
        if (currentDepth <= 10) {
            return 20;
        }
        return 5;
    }
}

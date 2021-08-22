package game.gameSetupOptions;

import board.moves.Move;

import java.util.Collections;
import java.util.List;

public class GameOptions
{
    public static final int CHECK_MATE_SCORE = -1000;
    public static final int STALE_MATE_SCORE = -100;

    public static final int MOVES_PERCENTAGE = 90;
    public static final int MINIMUM_MOVES    = 10;

    public static Move checkMate()
    {
        return new Move(CHECK_MATE_SCORE);
    }

    public static Move staleMate()
    {
        return new Move(STALE_MATE_SCORE);
    }

    public static List<Move> extractMoves(List<Move> moveList)
    {
        if (MOVES_PERCENTAGE < 80) {
            Collections.shuffle(moveList);
        }
        int movesNumber = Math.max(MINIMUM_MOVES, moveList.size() * MOVES_PERCENTAGE / 100);
        return moveList.subList(0, movesNumber);
    }
}

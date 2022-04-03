package game;

/**
 * Use it to compute the score of the move
 */
public class ScoreCalculator
{
    private static int score = 0;
    private static final int CASTLE_MOVE = 5;
    private static final int MOVES_NUMBER_BONUS_PAWN = 80;

    public static void addCastleMoveScore()
    {
        score += CASTLE_MOVE;
    }

    public static void withPawnMove(int movesNumber)
    {
        score += 1;
        if (movesNumber > MOVES_NUMBER_BONUS_PAWN) {
            score += 1;
        }
    }

    public static void withKnightOnFirstRow()
    {
        score += 10;
    }

    public static void resetScore()
    {
        score = 0;
    }

    public static int compute()
    {
        return score;
    }
}

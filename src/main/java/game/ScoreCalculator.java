package game;

/**
 * Use it to compute the score of the move
 */
public class ScoreCalculator
{
    private static int score = 0;
    private static final int CASTLE_MOVE = 5;

    public static void addCastleMoveScore()
    {
        score += CASTLE_MOVE;
    }

    public static void withPawnMove(int movesNumber)
    {
        score += 1;
        if (movesNumber > 80) {
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

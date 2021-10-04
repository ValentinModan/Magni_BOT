package game.kingcheck.attacked;

import board.OptimizedBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KingSafety
{

    private static final List<AttackedStrategy> attackedStrategyList = new ArrayList<>(Arrays.asList(
            new PawnAttackedStrategy(),
            new RookAttackedStrategy(),
            new KnightAttackedStrategy(),
            new BishopAttackedStrategy(),
            new QueenAttackedStrategy(),
            new KingAttackedStrategy()));

    public static int getNumberOfAttackers(OptimizedBoard board)
    {
        int numberOfAttackers = 0;

        for (AttackedStrategy attackedStrategy : attackedStrategyList) {
            if (attackedStrategy.isAttackingTheKing(board)) {
                numberOfAttackers++;
            }
            // You can not be attacked by more than two pieces
            if (numberOfAttackers > 1) {
                return numberOfAttackers;
            }
        }
        return numberOfAttackers;
    }

}

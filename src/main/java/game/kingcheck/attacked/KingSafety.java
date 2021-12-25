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
        for (AttackedStrategy attackedStrategy : attackedStrategyList) {
            if (attackedStrategy.isAttackingTheKing(board)) {
                return 1;
            }
        }
        return 0;
    }

}

package game.kingcheck.attacked;

import board.Board;

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

    public static boolean isTheKingAttacked(Board board)
    {

        for (AttackedStrategy attackedStrategy : attackedStrategyList) {
            if (attackedStrategy.isAttackingTheKing(board)) {
                return true;
            }
        }
        return false;
    }

}

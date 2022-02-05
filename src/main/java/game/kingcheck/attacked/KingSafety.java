package game.kingcheck.attacked;

import board.Board;
import game.kingcheck.optimizedAttacked.OptimizedBishopAttackStrategy;
import game.kingcheck.optimizedAttacked.QueenAttackStrategy;
import game.kingcheck.optimizedAttacked.OptimizedRookAttackStrategy;

import java.util.*;

public class KingSafety
{
    private static final List<AttackStrategy> ATTACK_STRATEGY_LIST = new ArrayList<>(Arrays.asList(
            new QueenAttackStrategy(),
            new OptimizedBishopAttackStrategy(),
            new OptimizedRookAttackStrategy(),
            new KnightAttackStrategy(),
            new PawnAttackStrategy(),
            new KingAttackStrategy()));

    public static boolean isTheKingAttacked(Board board)
    {
        for (AttackStrategy attackStrategy : ATTACK_STRATEGY_LIST) {
            if (attackStrategy.isAttackingTheKing(board)) {
                return true;
            }
        }
        return false;
    }

}

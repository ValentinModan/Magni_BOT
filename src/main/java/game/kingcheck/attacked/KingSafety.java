package game.kingcheck.attacked;

import board.Board;
import ch.qos.logback.core.util.TimeUtil;
import game.kingcheck.optimizedAttacked.OptimizedBishopAttackedStrategy;
import game.kingcheck.optimizedAttacked.OptimizedQueenAttackedStrategy;
import game.kingcheck.optimizedAttacked.OptimizedRookAttackedStrategy;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class KingSafety
{
    public static int miliseconds = 0;
    private static final List<AttackedStrategy> attackedStrategyList = new ArrayList<>(Arrays.asList(
            new OptimizedQueenAttackedStrategy(),
            new OptimizedBishopAttackedStrategy(),
            new OptimizedRookAttackedStrategy(),
            new KnightAttackedStrategy(),
            new PawnAttackedStrategy(),
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

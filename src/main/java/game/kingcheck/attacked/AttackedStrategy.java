package game.kingcheck.attacked;

import board.OptimizedBoard;

public interface AttackedStrategy {

    boolean isAttackingTheKing(OptimizedBoard board);
}

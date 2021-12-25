package game.kingcheck.attacked;

import board.OptimizedBoard;

//todo: Is this interface really necessary?
public interface AttackedStrategy {

    boolean isAttackingTheKing(OptimizedBoard board);
}

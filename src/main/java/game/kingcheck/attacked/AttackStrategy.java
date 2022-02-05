package game.kingcheck.attacked;

import board.Board;

//todo: Is this interface really necessary?
public interface AttackStrategy
{
    boolean isAttackingTheKing(Board board);
}

package game.kingcheck.attacked;

import board.Board;

//todo: Is this interface really necessary?
public interface AttackedStrategy {

    boolean isAttackingTheKing(Board board);
}

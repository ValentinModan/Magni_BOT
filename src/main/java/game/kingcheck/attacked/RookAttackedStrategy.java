package game.kingcheck.attacked;

import board.Board;

public class RookAttackedStrategy implements AttackedStrategy{
    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {
        return false;
    }
}

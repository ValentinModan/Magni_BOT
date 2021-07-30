package game.kingcheck.attacked;

import board.Board;

public class QueenAttackedStrategy implements AttackedStrategy{

    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {
        return false;
    }
}

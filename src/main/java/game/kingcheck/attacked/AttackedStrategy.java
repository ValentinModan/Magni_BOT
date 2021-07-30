package game.kingcheck.attacked;

import board.Board;
import board.pieces.Piece;

public interface AttackedStrategy {

    boolean isAttackingTheKing(Board board, boolean isWhiteKing);
}

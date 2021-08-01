package game.kingcheck.attacked;

import board.Board;
import board.OptimizedBoard;
import board.pieces.Piece;

public interface AttackedStrategy {

    boolean isAttackingTheKing(OptimizedBoard board);
}

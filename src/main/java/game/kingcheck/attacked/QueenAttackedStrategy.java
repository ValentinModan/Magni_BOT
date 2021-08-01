package game.kingcheck.attacked;

import board.Board;
import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;
import board.pieces.Queen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueenAttackedStrategy implements AttackedStrategy{

    List<Movement> queenMovementList = new ArrayList<>(Arrays.asList(
            Movement.UP,
            Movement.UP_LEFT,
            Movement.LEFT,
            Movement.LEFT_DOWN,
            Movement.DOWN,
            Movement.DOWN_RIGHT,
            Movement.RIGHT,
            Movement.UP_RIGHT));

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {

        boolean isWhiteKing = board.isWhiteToMove();
        Position kingPosition = board.getKing(isWhiteKing);

        for(Movement movement:queenMovementList)
        {
            if(XrayAttack.isXRayAttacked(board,kingPosition,movement,isWhiteKing, PieceType.QUEEN))
            {
                return true;
            }
        }
        return false;

    }
}

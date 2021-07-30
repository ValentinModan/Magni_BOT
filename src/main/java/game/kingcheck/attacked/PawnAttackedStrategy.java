package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;

import java.util.Arrays;
import java.util.List;

public class PawnAttackedStrategy implements AttackedStrategy {


    @Override
    public boolean isAttackingTheKing(Board board, boolean isWhiteKing) {

        Position kingPosition = board.getKing(isWhiteKing);

        List<Movement> movementList = getMovementList(isWhiteKing);

        for(Movement movement:movementList)
        {
            Piece possibleAttackingPiece = board.getPosition(kingPosition.move(movement));
            if(possibleAttackingPiece!=null && possibleAttackingPiece.isPawn() && possibleAttackingPiece.isWhite()!=isWhiteKing)
            {
                return true;
            }
        }
        return false;
    }

    private List<Movement> getMovementList(boolean isWhiteKing) {
        if (isWhiteKing) {
            return Arrays.asList(Movement.UP_LEFT, Movement.UP_RIGHT);
        }
        return Arrays.asList(Movement.DOWN_LEFT, Movement.DOWN_RIGHT);
    }
}

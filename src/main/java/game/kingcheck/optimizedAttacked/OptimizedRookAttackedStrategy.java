package game.kingcheck.optimizedAttacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.PieceType;
import game.kingcheck.attacked.AttackedStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class OptimizedRookAttackedStrategy implements AttackedStrategy
{
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        List<Position> rookPositionList = board.getTakenPiecesMap().keySet().stream()
                .filter(position ->board.getTakenPiecesMap().get(position) != null &&  board.getTakenPiecesMap().get(position).getPieceType() == PieceType.ROOK).collect(Collectors.toList());
        if (rookPositionList.isEmpty()) {
            return false;
        }
        Position kingPosition = board.getKingPosition();
        for (Position rookPosition : rookPositionList) {
            Movement movement = rookPosition.lineDirection(kingPosition);
            if (movement == null) {
                continue;
            }
            Position nextPosition = rookPosition.move(movement);
            while (!nextPosition.equals(kingPosition)) {
                if (board.pieceExistsAt(nextPosition)) {
                    return false;
                }
                nextPosition = nextPosition.move(movement);
            }
            if(nextPosition.equals(kingPosition))
            {
                return true;
            }
        }
        return false;
    }
}

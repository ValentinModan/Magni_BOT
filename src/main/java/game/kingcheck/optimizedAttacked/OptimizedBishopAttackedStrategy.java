package game.kingcheck.optimizedAttacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.PieceType;
import game.kingcheck.attacked.AttackedStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class OptimizedBishopAttackedStrategy implements AttackedStrategy
{
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        List<Position> bishopPositionList = board.getTakenPiecesMap().keySet().stream()
                .filter(position ->board.getTakenPiecesMap().get(position) != null &&  board.getTakenPiecesMap().get(position).getPieceType() == PieceType.BISHOP).collect(Collectors.toList());
        if (bishopPositionList.isEmpty()) {
            return false;
        }

        for (Position bishopPosition : bishopPositionList) {
            Position kingPosition = board.getKingPosition();
            Movement movement = bishopPosition.diagonalDirection(kingPosition);
            if (movement == null) {
                continue;
            }
            Position nextPosition = bishopPosition.move(movement);
            while (!nextPosition.equals(kingPosition)) {
                if (board.pieceExistsAt(nextPosition)) {
                    return false;
                }
                nextPosition = nextPosition.move(movement);
            }
            if (nextPosition.equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }
}

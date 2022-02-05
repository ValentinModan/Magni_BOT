package game.kingcheck.optimizedAttacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.pieces.PieceType;
import game.kingcheck.attacked.AttackStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class QueenAttackStrategy implements AttackStrategy
{
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        List<Position> queenPositionList = board.getTakenPiecesMap().keySet().stream()
                .filter(position -> board.getTakenPiecesMap().get(position) != null && board.getTakenPiecesMap().get(position).getPieceType() == PieceType.QUEEN)
                .collect(Collectors.toList());
        if (queenPositionList.isEmpty()) {
            return false;
        }

        for (Position queenPosition : queenPositionList) {
            Position kingPosition = board.getKingPosition();
            Movement movement = queenPosition.diagonalDirection(kingPosition);
            if (movement == null) {
                movement = queenPosition.lineDirection(kingPosition);
                if (movement == null) {
                    continue;
                }
            }
            Position nextPosition = queenPosition.move(movement);
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

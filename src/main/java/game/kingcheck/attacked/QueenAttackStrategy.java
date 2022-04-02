package game.kingcheck.attacked;

import board.Board;
import board.BoardHelper;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.moves.pieces.QueenMovement;
import board.pieces.Piece;

import java.util.List;
import java.util.stream.Collectors;

import static board.BoardHelper.opponentHas;
import static board.pieces.PieceType.QUEEN;

public class QueenAttackStrategy implements AttackStrategy
{
    private static final List<Movement> queenMovementList = QueenMovement.getInstance().getMovements();

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!opponentHas(board, QUEEN)) {
            return false;
        }
        for (Movement movement : queenMovementList) {
            if (Xray.isXRayAttacked(board, movement, QUEEN)) {
                return true;
            }
        }
        return false;
    }
}

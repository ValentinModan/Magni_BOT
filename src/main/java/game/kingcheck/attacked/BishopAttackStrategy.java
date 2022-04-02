package game.kingcheck.attacked;

import board.Board;
import board.moves.Movement;
import board.moves.calculator.pieces.BishopMoveCalculator;
import board.moves.pieces.BishopMovement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Piece;

import java.util.List;
import java.util.stream.Collectors;

import static board.pieces.PieceType.BISHOP;

public class BishopAttackStrategy implements AttackStrategy
{
    final static List<Movement> bishopMovementList = BishopMovement.getInstance().getMovements();

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(BISHOP)) {
            return false;
        }
        for (Movement movement : bishopMovementList) {
            if (Xray.isXRayAttacked(board, movement, BISHOP)) {
                return true;
            }
        }
        return false;
    }
}

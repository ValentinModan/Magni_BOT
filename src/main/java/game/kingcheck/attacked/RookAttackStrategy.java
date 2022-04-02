package game.kingcheck.attacked;

import board.Board;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.moves.pieces.RookMovement;
import board.pieces.Piece;

import java.util.List;
import java.util.stream.Collectors;

import static board.pieces.PieceType.ROOK;

public class RookAttackStrategy implements AttackStrategy
{
    private static final List<Movement> rookMovementList = RookMovement.getInstance().getMovements();

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(ROOK)) {
            return false;
        }
        for (Movement movement : rookMovementList) {
            if (Xray.isXRayAttacked(board, movement, ROOK)) {
                return true;
            }
        }
        return false;
    }
}

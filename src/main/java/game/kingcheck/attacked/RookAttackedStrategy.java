package game.kingcheck.attacked;

import board.Board;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;
import java.util.stream.Collectors;

public class RookAttackedStrategy implements AttackedStrategy
{
    private static final List<Movement> rookMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.ROOK);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(PieceType.ROOK)) {
            return false;
        }
        for (Movement movement : rookMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.ROOK)) {
                return true;
            }
        }
        return false;
    }
}

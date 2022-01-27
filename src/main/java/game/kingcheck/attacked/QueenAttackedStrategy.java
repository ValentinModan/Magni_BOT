package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;
import java.util.stream.Collectors;

public class QueenAttackedStrategy implements AttackedStrategy
{
    private static final List<Movement> queenMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.QUEEN);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(PieceType.QUEEN)) {
            return false;
        }
        for (Movement movement : queenMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.QUEEN)) {
                return true;
            }
        }
        return false;
    }
}

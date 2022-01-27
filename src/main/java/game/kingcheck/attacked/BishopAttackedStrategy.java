package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BishopAttackedStrategy implements AttackedStrategy
{

    final static List<Movement> bishopMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.BISHOP);

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!board.getTakenPiecesMap().values().stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toList()).contains(PieceType.BISHOP)) {
            return false;
        }
        for (Movement movement : bishopMovementList) {
            if (Xray.isXRayAttacked(board, movement, PieceType.BISHOP)) {
                return true;
            }
        }
        return false;
    }
}

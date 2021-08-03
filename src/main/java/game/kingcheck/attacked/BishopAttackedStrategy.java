package game.kingcheck.attacked;

import board.Board;
import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.moves.calculator.PossibleMovesCalculator;
import board.moves.pieces.MovementCalculator;
import board.pieces.EmptyPiece;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BishopAttackedStrategy implements AttackedStrategy {

    List<Movement> bishopMovementList = MovementCalculator.getPossibleMoves(EmptyPiece.BISHOP);

    @Override
    public boolean isAttackingTheKing(OptimizedBoard board) {

        boolean isWhiteKing = board.isWhiteToMove();

        Position kingPosition = board.getKing(isWhiteKing);
        for (Movement movement : bishopMovementList) {
            if (isDiagonallyAttacked(board, kingPosition, movement, isWhiteKing,PieceType.BISHOP))
            {
                return true;
            }
            XrayAttack.isXRayAttacked(board, kingPosition, movement, isWhiteKing,PieceType.BISHOP);
        }
        return false;
    }

    public boolean isDiagonallyAttacked(OptimizedBoard board, Position currentPosition, Movement movement, boolean isWhiteKing, PieceType pieceType) {
        currentPosition = currentPosition.move(movement);
        if (!currentPosition.isValid()) {
            return false;
        }
        Piece piece = board.getPiece(currentPosition);
        if (piece != null) {
            return piece.getPieceType()== pieceType && piece.isWhite() != isWhiteKing;
        }
        return isDiagonallyAttacked(board, currentPosition, movement, isWhiteKing, pieceType);
    }
}

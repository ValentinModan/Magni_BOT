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
            if(XrayAttack.isXRayAttacked(board, kingPosition, movement, isWhiteKing,PieceType.BISHOP))
            {
                return true;
            }
        }
        return false;
    }
}

package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

public class XrayAttack {

    //up,down,left,right and diagonal
    public static boolean isXRayAttacked(OptimizedBoard board, Position currentPosition, Movement movement, boolean isWhiteKing,PieceType pieceType) {
        currentPosition = currentPosition.move(movement);
        if (!currentPosition.isValid()) {
            return false;
        }
        Piece piece = board.getPiece(currentPosition);
        if (piece != null) {
            return piece.getPieceType()== pieceType && piece.isWhite() != isWhiteKing;
        }
        return isXRayAttacked(board, currentPosition, movement, isWhiteKing,pieceType);
    }
}

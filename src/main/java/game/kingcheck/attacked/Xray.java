package game.kingcheck.attacked;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class Xray
{

    //up, down, left, right and diagonal
    public static boolean isXRayAttacked(OptimizedBoard board, Position currentPosition, Movement movement, boolean isWhiteKing, PieceType pieceType)
    {
        currentPosition = currentPosition.move(movement);
        if (!currentPosition.isValid()) {
            return false;
        }
        Piece piece = board.getTakenPiecesMap().get(currentPosition);
        if (piece != null && piece.getPieceType() == pieceType) {
            return true;
        }
        piece = board.getMovingPiece(currentPosition);
        if (piece != null) {
            return false;
        }
        return isXRayAttacked(board, currentPosition, movement, isWhiteKing, pieceType);
    }

    public static List<Move> xRayMoveList(OptimizedBoard optimizedBoard, Position initialPosition, List<Movement> movementList)
    {
        List<Move> moveList = new ArrayList<>();
        for (Movement movement : movementList) {
            xRayMoves(optimizedBoard, moveList, initialPosition, initialPosition, movement);
        }
        return moveList;
    }

    public static void xRayMoves(OptimizedBoard board, List<Move> moveList, Position initialPosition, Position currentPosition, Movement movement)
    {
        currentPosition = currentPosition.move(movement);

        if (!currentPosition.isValid()) {
            return;
        }
        Piece takenPiece  = board.getTakenPiecesMap().get(currentPosition);
        Move  currentMove = new Move(initialPosition, currentPosition);

        if (takenPiece == null && board.getMovingPiece(currentPosition) == null) {
            moveList.add(currentMove);
            xRayMoves(board, moveList, initialPosition, currentPosition, movement);
            return;
        }
        if (isOppositePiece(takenPiece, board.isWhiteToMove())) {
            moveList.add(currentMove);
        }
    }

    private static boolean isOppositePiece(Piece piece, boolean isWhitePiece)
    {
        if (piece != null && isWhitePiece != piece.isWhite()) {
            return true;
        }
        return false;
    }
}

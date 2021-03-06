package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.LinkedList;
import java.util.List;

public class Xray
{
    public static List<Move> xRayMoveList(Board board, Position initialPosition, List<Movement> movementList)
    {
        List<Move> moveList = new LinkedList<>();
        for (Movement movement : movementList) {
            xRayMoves(board, moveList, initialPosition, initialPosition, movement);
        }
        return moveList;
    }

    private static void xRayMoves(Board board, List<Move> moveList, Position initialPosition, Position currentPosition, Movement movement)
    {
        currentPosition = currentPosition.move(movement);

        while (currentPosition.isValid()) {
            //found a piece of the same color
            if (board.getMovingPiece(currentPosition) != null) {
                return;
            }
            moveList.add(new Move(initialPosition, currentPosition));
            //found an enemy piece
            if (board.getTakenPiecesMap().get(currentPosition) != null) {
                return;
            }
            currentPosition = currentPosition.move(movement);
        }
    }

    //up, down, left, right and diagonals
    public static boolean isXRayAttacked(Board board, Movement movement, PieceType pieceType)
    {
        Position currentPosition = board.getKingPosition();
        currentPosition = currentPosition.move(movement);

        while (currentPosition.isValid()) {
            Piece takenPiece = board.getTakenPiecesMap().get(currentPosition);
            if (takenPiece != null) {
                return takenPiece.getPieceType() == pieceType;
            }
            if (board.getMovingPiece(currentPosition) != null) {
                return false;
            }
            currentPosition = currentPosition.move(movement);
        }
        return false;
    }

}

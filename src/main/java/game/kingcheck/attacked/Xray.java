package game.kingcheck.attacked;

import board.Board;
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

    public static List<Move> xRayMoveList(Board board, Position initialPosition, List<Movement> movementList)
    {
        List<Move> moveList = new ArrayList<>();
        for (Movement movement : movementList) {
            xRayMoves(board, moveList, initialPosition, initialPosition, movement);
        }
        return moveList;
    }

    public static void xRayMoves(Board board, List<Move> moveList, Position initialPosition, Position currentPosition, Movement movement)
    {
        currentPosition = currentPosition.move(movement);

        if (!currentPosition.isValid()) {
            return;
        }
        Piece takenPiece = board.getTakenPiecesMap().get(currentPosition);
        Move currentMove = new Move(initialPosition, currentPosition);

        if (takenPiece == null && board.getMovingPiece(currentPosition) == null) {
            moveList.add(currentMove);
            xRayMoves(board, moveList, initialPosition, currentPosition, movement);
            return;
        }
        if (board.getKing() != null && board.getKing().isOpponentOf(takenPiece)) {

           //todo check if this is a good thing
            //currentMove.setTakenPiece(takenPiece);
            moveList.add(currentMove);
        }
    }

}

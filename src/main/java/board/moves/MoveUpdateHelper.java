package board.moves;

import board.OptimizedBoard;
import board.Position;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;

public class MoveUpdateHelper
{
    public static void moveUpdate(OptimizedBoard optimizedBoard, Move move)
    {
        updateMovingPiece(optimizedBoard, move);
        updateTakenPiece(optimizedBoard, move);

        updateCheckMate(move);
        updateCastleMove(move);
        updateAnPassantMove(optimizedBoard, move);
    }

    private static void updateMovingPiece(OptimizedBoard board, Move move)
    {
        Piece movingPiece = board.getMovingPiecesMap().get(move.getInitialPosition());
        move.setMovingPiece(movingPiece);
    }

    public static void updateCheckMate(Move move)
    {
        Piece takenPiece = move.getTakenPiece();
        if (takenPiece != null && takenPiece.getPieceType() == PieceType.KING) {
            move.setCheckMate(true);
            move.setScore(1000);
        }
    }

    private static void updateTakenPiece(OptimizedBoard board, Move move)
    {
        Piece takenPiece = board.getTakenPiecesMap().get(move.getFinalPosition());
        move.setTakenPiece(takenPiece);
    }

    public static void updateAnPassantMove(OptimizedBoard board, Move move)
    {
        Position initialPosition = move.getInitialPosition();
        Piece    takenPiece      = move.getTakenPiece();
        if (move.getMovingPiece().getPieceType() != PieceType.PAWN) {
            return;
        }

        Pawn     pawn     = (Pawn) move.getMovingPiece();
        Movement movement = initialPosition.getDiagonalMovement(move.getFinalPosition());
        Movement line     = movement.lineFromDiagonal();

        if (takenPiece != null) {
            return;
        }
        Position linePosition = initialPosition.move(line);


        Piece linePiece = board.getTakenPiecesMap().get(linePosition);
        if (linePiece == null || linePiece.getPieceType() != PieceType.PAWN) {
            return;
        }


        Move lastMove = board.lastMove();
        if (lastMove == null) {
            return;
        }

        //last pawn move was here
        if (lastMove.getFinalPosition().equals(linePosition)) {
            if (lastMove.getInitialPosition().equals(linePosition.move(Movement.upTwo(pawn.isWhite())))) {
                move.setAnPassant(true);
                move.setTakenAnPassant(linePosition);
            }

        }
    }

    private static void updateCastleMove(Move move)
    {
        Piece movingPiece = move.getMovingPiece();
        Piece takenPiece  = move.getTakenPiece();

        if (takenPiece == null) {
            return;
        }
        if (movingPiece == null) {
            System.err.println("Invalid move " + move);
        }
        if (movingPiece.getPieceType() == PieceType.KING) {
            if (takenPiece.getPieceType() == PieceType.ROOK) {
                if (movingPiece.isWhite() == movingPiece.isWhite()) {
                    move.setCastleMove(true);
                }
            }
        }
    }
}

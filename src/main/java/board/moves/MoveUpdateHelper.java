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
        updatePawnPromotion(move);
        updateCastleMove(optimizedBoard, move);
        updateAnPassantMove(optimizedBoard, move);
    }

    public static void updatePawnPromotion(Move move)
    {
        if (move.getMovingPiece() == null || move.getMovingPiece().getPieceType() != PieceType.PAWN) {
            return;
        }
        if (move.getMovingPiece().isWhite() && move.getInitialPosition().getRow() == 7) {
            move.setPawnPromotion(true);
            move.setScore(move.getMovingPiece().getScore());
        }
        if (!move.getMovingPiece().isWhite() && move.getInitialPosition().getRow() == 2) {
            move.setPawnPromotion(true);
        }
    }

    private static void updateMovingPiece(OptimizedBoard board, Move move)
    {
        Piece movingPiece = board.getMovingPiece(move.getInitialPosition());
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
        if (takenPiece != null) {
            move.setScore(takenPiece.getScore());
        }
    }

    public static void updateAnPassantMove(OptimizedBoard board, Move move)
    {
        Position initialPosition = move.getInitialPosition();
        Piece    takenPiece      = move.getTakenPiece();

        if (move.getMovingPiece() == null || move.getMovingPiece().getPieceType() != PieceType.PAWN) {
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

    private static void updateCastleMove(OptimizedBoard optimizedBoard, Move move)
    {
        Piece movingPiece = move.getMovingPiece();
        Piece rookPiece   = optimizedBoard.getMovingPiece(move.getFinalPosition());

        if (rookPiece == null) {
            return;
        }
        if (movingPiece.getPieceType() == PieceType.KING) {
            if (rookPiece.getPieceType() == PieceType.ROOK) {
                if (movingPiece.isWhite() == movingPiece.isWhite()) {
                    move.setCastleMove(true);
                }
            }

            //todo improve checks here
            int distance = move.getInitialPosition().getColumn() - move.getFinalPosition().getColumn();
            if (distance == 2 || distance == 3) {
                move.setCastleMove(true);
            }
        }
    }
}

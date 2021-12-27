package board.moves;

import board.Board;
import board.Position;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;

public class MoveUpdateHelper
{
    public static void moveUpdate(Board board, Move move)
    {
        updateMovingPiece(board, move);
        updateTakenPiece(board, move);

        updateCheckMate(move);
        updatePawnPromotion(move);
        updateCastleMove(board, move);
        updateAnPassantMove(board, move);
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
            move.setScore(move.getMovingPiece().getScore());
        }
    }

    private static void updateMovingPiece(Board board, Move move)
    {
        Piece movingPiece = board.getMovingPiece(move.getInitialPosition());
        move.setMovingPiece(movingPiece);
    }

    public static void updateCheckMate(Move move)
    {
        Piece takenPiece = move.getTakenPiece();
        if (takenPiece != null && takenPiece.getPieceType() == PieceType.KING) {
            move.setCheckMate(true);
            //TODO: what?
            move.setScore(666);
        }
    }

    private static void updateTakenPiece(Board board, Move move)
    {
        Piece takenPiece = board.getTakenPiecesMap().get(move.getFinalPosition());
        move.setTakenPiece(takenPiece);
        if (takenPiece != null) {
            move.setScore(takenPiece.getScore());
        }
    }

    public static void updateAnPassantMove(Board board, Move move)
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

    private static void updateCastleMove(Board board, Move move)
    {
        Piece movingPiece = move.getMovingPiece();
        Piece rookPiece   = board.getMovingPiece(move.getFinalPosition());

        if(movingPiece ==null)
        {
            System.err.println("Invalid move"+ move);
            System.err.println(board);
            System.err.println(board.allMoves);
        }
        if (rookPiece == null) {
            return;
        }
        try {
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
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

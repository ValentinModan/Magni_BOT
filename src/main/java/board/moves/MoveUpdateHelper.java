package board.moves;

import board.Board;
import board.Position;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.gameSetupOptions.GameOptions;
import lombok.extern.slf4j.Slf4j;

import static board.pieces.PieceType.KING;
import static board.pieces.PieceType.ROOK;

@Slf4j
public class MoveUpdateHelper
{
    public static void moveUpdate(Board board, Move move)
    {
        updateMovingPiece(board, move);
        updateTakenPiece(board, move);

        updateCheckMate(move);
        updatePawnPromotion(move);
        updateCastleMove(board, move);
        updateEnPassantMove(board, move);
    }

    public static void updatePawnPromotion(Move move)
    {
        if (move.getMovingPiece().getPieceType() != PieceType.PAWN) {
            return;
        }
        if (move.getMovingPiece().isWhite() && move.getInitialPosition().getRow() == 7) {
            move.setPawnPromotion(true);
        }
        if (!move.getMovingPiece().isWhite() && move.getInitialPosition().getRow() == 2) {
            move.setPawnPromotion(true);
        }
    }

    private static void updateMovingPiece(Board board, Move move)
    {
        Piece movingPiece = board.getMovingPiece(move.getInitialPosition());

        if (movingPiece == null) {
            throw new NullPointerException(board + "\n" + move);
        }
        move.setMovingPiece(movingPiece);
    }

    public static void updateCheckMate(Move move)
    {
        Piece takenPiece = move.getTakenPiece();
        if (takenPiece != null && takenPiece.getPieceType() == KING) {
            move.setCheckMate(true);
            move.setScore(GameOptions.CHECK_MATE_SCORE);
        }
    }

    private static void updateTakenPiece(Board board, Move move)
    {
        Piece takenPiece = board.getTakenPiecesMap().get(move.getFinalPosition());
        move.setTakenPiece(takenPiece);
    }

    public static void updateEnPassantMove(Board board, Move move)
    {
        Position initialPosition = move.getInitialPosition();

        if (move.getMovingPiece().getPieceType() != PieceType.PAWN) {
            return;
        }
        Pawn pawn = (Pawn) move.getMovingPiece();
        Movement movement = initialPosition.getDiagonalMovement(move.getFinalPosition());
        Movement line = movement.lineFromDiagonal();

        if (move.getTakenPiece() != null) {
            return;
        }
        Position linePosition = initialPosition.move(line);


        Piece linePiece = board.getTakenPiecesMap().get(linePosition);
        if (linePiece == null || linePiece.getPieceType() != PieceType.PAWN) {
            return;
        }
        Move lastMove = board.lastMove();
        //last pawn move was here
        if (lastMove != null && lastMove.getFinalPosition().equals(linePosition)) {
            if (lastMove.getInitialPosition().equals(linePosition.move(Movement.upTwo(pawn.isWhite())))) {
                move.setEnPassant(true);
                move.setTakenAnPassant(linePosition);
            }
        }
    }

    //todo improve here
    private static void updateCastleMove(Board board, Move move)
    {
        Piece movingKing = move.getMovingPiece();

        if (movingKing.getPieceType() != KING) {
            return;
        }
        Movement direction = move.getInitialPosition().castleDirection(move.getFinalPosition());
        //castling using the final position of the king
        if (move.getInitialPosition().move(direction).move(direction).equals(move.getFinalPosition())) {
            move.setCastleMove(true);
            return;
        }
        Piece rookPiece = board.getMovingPiece(move.getFinalPosition());
        if (rookPiece != null && rookPiece.getPieceType() == ROOK) {
            if (!movingKing.isOpponentOf(rookPiece)) {
                move.setCastleMove(true);
            }
        }

    }
}

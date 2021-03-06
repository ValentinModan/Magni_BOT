package board.moves;

import board.Board;
import board.ColorEnum;
import board.Position;
import board.moves.movetypes.Move;
import board.pieces.*;
import game.gameSetupOptions.GameOptions;
import lombok.extern.slf4j.Slf4j;
import pieces.PieceFactory;

import static board.ColorEnum.BLACK;
import static board.ColorEnum.WHITE;
import static board.pieces.PieceType.*;

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
        ColorEnum colorEnum = move.getMovingPiece().isWhite() ? WHITE : ColorEnum.BLACK;
        if (move.getMovingPiece().getPieceType() != PAWN) {
            return;
        }
        if ((colorEnum == WHITE) && (move.getInitialPosition().getRow() == 7)) {
            move.setPawnPromotion(true);
        }
        if (colorEnum == BLACK && move.getInitialPosition().getRow() == 2) {
            move.setPawnPromotion(true);
        }
        if (move.isPawnPromotion() && move.getPromotionPiece() == null) {
            PieceType pieceType = getPieceTypeFromCharacter(move.getPromotionSmithNotation());
            move.setPromotionPiece(PieceFactory.createPiece(pieceType, move.getMovingPiece().isWhite()));
        }
    }

    private static PieceType getPieceTypeFromCharacter(char c)
    {
        switch (c) {
            case 'N':
                return KNIGHT;
            case 'R':
                return ROOK;
            case 'B':
                return BISHOP;
            case 'Q':
            default:
                return QUEEN;
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
            move.setCheckMate();
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

        if (move.getMovingPiece().getPieceType() != PAWN) {
            return;
        }
        Pawn pawn = (Pawn) move.getMovingPiece();
        Movement movement = initialPosition.getDiagonalMovement(move.getFinalPosition());
        if (movement == null) {
            return;
        }
        Movement line = movement.lineFromDiagonal();

        if (move.getTakenPiece() != null) {
            return;
        }
        Position linePosition = initialPosition.move(line);

        Piece linePiece = board.getTakenPiecesMap().get(linePosition);
        if (linePiece == null || linePiece.getPieceType() != PAWN) {
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

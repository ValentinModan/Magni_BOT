package board.moves.controller;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import board.moves.Movement;
import board.pieces.*;
import lombok.SneakyThrows;

public class MoveController
{
    public void undoMove(Board board, Move move)
    {
        board.allMoves.remove(board.allMoves.size() - 1);
        if (move.getMovingPiece().getPieceType() == PieceType.KING) {
            board.updateKingPosition(move.getInitialPosition());
        }

        //put the piece back where it was
        if (move.isPawnPromotion()) {
            //put a pawn back
            board.getMovingPiecesMap().put(move.getInitialPosition(), new Pawn(move.getMovingPiece().isWhite()));
        }
        else {
            board.getMovingPiecesMap().put(move.getInitialPosition(), move.getMovingPiece());
        }

        //clear moved piece position
        board.getMovingPiecesMap().remove(move.getFinalPosition());

        if (move.isCastleMove()) {
            Movement direction = move.getInitialPosition().castleDirection(move.getFinalPosition()).opposite();
            //maybe this will be wrong
            board.getMovingPiecesMap().remove(move.getInitialPosition().move(direction));
            board.addPiece(move.getFinalPosition(), new Rook(move.getMovingPiece().isWhite()));
        }

        if (move.isEnPassant()) {
            board.addPiece(move.getTakenAnPassant(), new Pawn(!move.getMovingPiece().isWhite()));
        }

        Piece takenPiece = move.getTakenPiece();

        if (takenPiece != null) {
            board.getTakenPiecesMap().put(move.getFinalPosition(), move.getTakenPiece());
        }
    }

    public void move(Board board, Move move)
    {
        MoveUpdateHelper.moveUpdate(board, move);

        //TODO: make strategy for move type
        if (move.isCastleMove()) {
            castleMove(board, move);
            board.allMoves.add(move);
            return;
        }
        if (move.isEnPassant()) {
            anPassant(board, move);
            board.allMoves.add(move);
            return;
        }
        if (move.isPawnPromotion()) {
            pawnPromotion(board, move);
            board.allMoves.add(move);
            return;
        }

        //move moving piece
        board.getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());
        //clear original position
        board.getMovingPiecesMap().remove(move.getInitialPosition());

        //clear taken position
        board.getTakenPiecesMap().remove(move.getFinalPosition());

        if (move.getMovingPiece().getPieceType() == PieceType.KING) {
            board.updateKingPosition(move.getFinalPosition());
        }
        board.allMoves.add(move);
    }

    private void pawnPromotion(Board board, Move move)
    {
        //move moving piece
        board.getMovingPiecesMap().put(move.getFinalPosition(), move.getPromotionPiece());
        //clear original position
        board.getMovingPiecesMap().remove(move.getInitialPosition());

        //clear taken position
        board.getTakenPiecesMap().remove(move.getFinalPosition());
    }


    private void anPassant(Board board, Move move)
    {
        //add pawn to new position
        board.getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());

        //remove taken pawn
        board.getTakenPiecesMap().remove(move.getTakenAnPassant());

        //remove initial position
        board.getMovingPiecesMap().remove(move.getInitialPosition());
    }

    //TODO: add actual method to move piece (put and remove)
    private void castleMove(Board board, Move move)
    {
        Movement direction = move.getInitialPosition().castleDirection(move.getFinalPosition());

        Position kingInitial = move.getInitialPosition();
        Position finalPositionKing = kingInitial.move(direction).move(direction);

        Position rookInitial = move.getFinalPosition();
        if (board.getMovingPiecesMap().get(rookInitial) == null) {
            rookInitial = rookInitial.move(direction);
        }
        Position rookFinal = finalPositionKing.move(direction.opposite());

        //move the king
        board.getMovingPiecesMap().put(finalPositionKing, move.getMovingPiece());
        board.getMovingPiecesMap().remove(kingInitial);

        //move rook
        board.getMovingPiecesMap().put(rookFinal, board.getMovingPiece(rookInitial));
        board.getMovingPiecesMap().remove(rookInitial);

        //update king position
        board.updateKingPosition(finalPositionKing);
    }
}

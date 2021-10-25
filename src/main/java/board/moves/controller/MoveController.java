package board.moves.controller;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import board.moves.Movement;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;
import board.pieces.Queen;

public class MoveController
{
    public void undoMove(OptimizedBoard board, Move move)
    {
        board.allMoves.remove(board.allMoves.size() - 1);
        if (move.getMovingPiece() != null && move.getMovingPiece().getPieceType() == PieceType.KING) {
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

        Piece takenPiece = move.getTakenPiece();

        if (takenPiece != null) {
            board.getTakenPiecesMap().put(move.getFinalPosition(), move.getTakenPiece());
        }
    }

    public void move(OptimizedBoard board, Move move)
    {
        MoveUpdateHelper.moveUpdate(board, move);

        //TODO: make strategy for move type
        if (move.isCastleMove()) {
            castleMove(board, move);
        }
        else if (move.isAnPassant()) {
            anPassant(board, move);
        }
        else if (move.isPawnPromotion()) {
            pawnPromotion(board, move);
        }
        else {
            //move moving piece
            board.getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());
            //clear original position
            board.getMovingPiecesMap().remove(move.getInitialPosition());

            //clear taken position
            board.getTakenPiecesMap().remove(move.getFinalPosition());

            if (move.getMovingPiece() != null && move.getMovingPiece().getPieceType() == PieceType.KING) {
                board.updateKingPosition(move.getFinalPosition());
            }
        }
        board.allMoves.add(move);
    }

    private void pawnPromotion(OptimizedBoard board, Move move)
    {
        //move moving piece
        board.getMovingPiecesMap().put(move.getFinalPosition(), new Queen(move.getMovingPiece().isWhite()));
        //clear original position
        board.getMovingPiecesMap().remove(move.getInitialPosition());

        //clear taken position
        board.getTakenPiecesMap().remove(move.getFinalPosition());
    }


    private void anPassant(OptimizedBoard optimizedBoard, Move move)
    {
        //add pawn to new position
        optimizedBoard.getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());

        //remove taken pawn
        optimizedBoard.getTakenPiecesMap().remove(move.getTakenAnPassant());

        //remove initial position
        optimizedBoard.getMovingPiecesMap().remove(move.getInitialPosition());
    }

    //TODO: add actual method to move piece (put and remove)

    private void castleMove(OptimizedBoard board, Move move)
    {
        Movement direction = move.getInitialPosition().castleDirection(move.getFinalPosition());

        Position kingInitial       = move.getInitialPosition();
        Position finalPositionKing = kingInitial.move(direction).move(direction);
        Position rookInitial       = move.getFinalPosition();
        Position rookFinal         = finalPositionKing.move(direction.opposite());

        //move the king
        board.getMovingPiecesMap().put(finalPositionKing, move.getMovingPiece());
        board.getMovingPiecesMap().remove(kingInitial);

        //move rook
        board.getMovingPiecesMap().put(rookFinal, board.getMovingPiece(move.getFinalPosition()));
        board.getMovingPiecesMap().remove(rookInitial);

        //update king position
        board.updateKingPosition(finalPositionKing);
    }
}

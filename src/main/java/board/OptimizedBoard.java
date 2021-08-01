package board;

import board.moves.Move;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimizedBoard {

    private List<Move> possibleMoves;
    private Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private List<Move> allMoves = new ArrayList<>();

    private boolean isWhiteToMove;

    Position whiteKingPosition;

    Position blackKingPosition;



    public boolean isValidMove(Move move) {
        return possibleMoves.contains(move);
    }

    public Position getKing(boolean isWhite)
    {
        return isWhiteToMove? whiteKingPosition : blackKingPosition;
    }

    public void move(Move move) {

        if (!isValidMove(move)) {
            System.out.println("Warning, invalid move!");
        }
        updateMovingPiece(move);
        updateTakenPiece(move);
    }


    private void actualMove(Move move) {
        //move moving piece
        getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());
        //clear original position
        getMovingPiecesMap().put(move.getInitialPosition(),null);

        //clear taken position
        getTakenPiecesMap().put(move.getFinalPosition(),null);

        allMoves.add(move);


    }

    public void addPiece(Position position,Piece piece)
    {
        if(piece.isWhite())
        {
            whitePiecesMap.put(position,piece);
        }
        else
        {
            blackPiecesMap.put(position,piece);
        }
    }

    public Piece getPiece(Position position)
    {
        Piece piece = whitePiecesMap.get(position);
        return piece!=null?piece: blackPiecesMap.get(position);
    }

    public void computePossibleMoves()
    {

    }

    private void undoMove(Move move) {
        //put the piece back where it was
        getMovingPiecesMap().put(move.getInitialPosition(), move.getMovingPiece());
        //clear moved piece position
        getMovingPiecesMap().put(move.getFinalPosition(), null);

        getTakenPiecesMap().put(move.getFinalPosition(),move.getTakenPiece());
    }

    private Map<Position, Piece> getMovingPiecesMap() {
        return isWhiteToMove ? whitePiecesMap : blackPiecesMap;
    }

    private Map<Position, Piece> getTakenPiecesMap() {
        return isWhiteToMove ? blackPiecesMap : whitePiecesMap;
    }

    private void updateMovingPiece(Move move) {
        if (move.getMovingPiece() != null)
            return;

        if (isWhiteToMove) {
            move.setMovingPiece(whitePiecesMap.get(move.getInitialPosition()));
            return;
        }
        move.setMovingPiece(blackPiecesMap.get(move.getInitialPosition()));
    }

    private void updateTakenPiece(Move move) {
        if (move.getTakenPiece() != null)
            return;

        if (isWhiteToMove) {
            move.setTakenPiece(whitePiecesMap.get(move.getFinalPosition()));
            return;
        }
        move.setTakenPiece(blackPiecesMap.get(move.getFinalPosition()));
    }

    public boolean isWhiteToMove() {
        return isWhiteToMove;
    }

    public void setWhiteToMove(boolean whiteToMove) {
        isWhiteToMove = whiteToMove;
    }
}

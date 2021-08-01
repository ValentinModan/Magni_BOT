package board;

import board.moves.Move;
import board.pieces.Piece;

import java.util.*;

public class OptimizedBoard {

    private List<Move> possibleMoves = new ArrayList<>();
    private Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private static final String EMPTY_POSITION = ".  ";

    private List<Move> allMoves = new ArrayList<>();

    private boolean isWhiteToMove = true;

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
        actualMove(move);
    }


    private void actualMove(Move move) {
        //move moving piece
        getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());
        //clear original position
        getMovingPiecesMap().remove(move.getInitialPosition());

        //clear taken position
        getTakenPiecesMap().remove(move.getFinalPosition());

        allMoves.add(move);


    }

    public void undoMove(Move move) {
        //put the piece back where it was
        getMovingPiecesMap().put(move.getInitialPosition(), move.getMovingPiece());
        //clear moved piece position
        getMovingPiecesMap().remove(move.getFinalPosition());

        Piece takenPiece = move.getTakenPiece();

        if(takenPiece!=null)
        {
            getTakenPiecesMap().put(move.getFinalPosition(),move.getTakenPiece());
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptimizedBoard that = (OptimizedBoard) o;
        return whitePiecesMap.equals(that.whitePiecesMap) && blackPiecesMap.equals(that.blackPiecesMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whitePiecesMap, blackPiecesMap);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 8; i >= 1; i--) {
            for (char letter = 'a'; letter <= 'h'; letter++) {
                //TODO: refactor this
               Piece piece = getPiece(new Position(letter,i));
                String l = piece!=null? piece.toString()+ "  ": EMPTY_POSITION ;
                stringBuilder.append(l);
                //  stringBuilder.append(' ');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}



package board;

import board.moves.Move;
import board.moves.calculator.PossibleMovesCalculator;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements Cloneable {
    private Map<Position, Piece> boardMap = new HashMap<>();
    private static final String EMPTY_POSITION = ".  ";

    private Position whiteKingPosition;
    private Position blackKingPosition;

    boolean isWhiteToMove = true;

    private List<Position> whitePiecesPositions = new ArrayList<>();
    private List<Position> blackPiecesPositions = new ArrayList<>();

    private PossibleMovesCalculator possibleMovesCalculator = new PossibleMovesCalculator();

    private List<Move> possibleMoves;

    public void addPosition(Position position) {
        addPosition(position, null);
    }

    public void addPosition(Position position, Piece piece) {
        boardMap.put(position, piece);

        if (piece == null) {
            return;
        }
        if (piece.isKing()) {
            if (piece.isWhite()) {
                whiteKingPosition = position;
            } else {
                blackKingPosition = position;
            }
        }

        if (piece.isWhite()) {
            whitePiecesPositions.add(position);
        } else {
            blackPiecesPositions.add(position);
        }
    }

    public List<Position> getCurrentPlayerPositionList() {
        return isWhiteToMove ? whitePiecesPositions : blackPiecesPositions;
    }


    public Piece getPosition(String string) {
        if (string.length() != 2) {
            System.out.println("Error: Invalid position: " + string);
            return null;
        }
        return getPosition(new Position(string.charAt(0), (int) string.charAt(1)));
    }

    public void movePiece(String string) {
        movePiece(new Position(string.charAt(0), string.charAt(1) - '0'),
                new Position(string.charAt(2), (int) string.charAt(3) - '0'));
    }

    public void computePossibleMoves()
    {
        possibleMoves = possibleMovesCalculator.getPossibleMoves(this);
    }

    public boolean isMoveValid(Move move)
    {
        return  possibleMoves.contains(move);
    }

    public void movePiece(Position initialPosition, Position destinationPosition) {


        Piece destinationPiece = boardMap.get(destinationPosition);
        List<Position> colorPieceList = isWhiteToMove ? blackPiecesPositions : whitePiecesPositions;
        Piece movingPiece = boardMap.get(initialPosition);

        //keeping the pieces list updated
        if (destinationPiece != null && destinationPiece.isWhite() != isWhiteToMove) {

            boolean operationSuccessful = colorPieceList.remove(destinationPosition);
            if (!operationSuccessful) {
                System.out.println("Something was wrong then removing a piece:");
                System.out.println(whitePiecesPositions);
                System.out.println(destinationPiece);
            } else {
                System.out.println(movingPiece + " on " + initialPosition + " captures " + destinationPiece + " on " + destinationPosition);
            }
        }

        //moving the actual piece
        boardMap.put(destinationPosition, movingPiece);
        clearPosition(initialPosition);

        //swaping player
        isWhiteToMove = !isWhiteToMove;

    }


    private void clearPosition(Position position) {
        boardMap.put(position, null);
    }


    public Piece getPosition(Position position) {
        return boardMap.get(position);
    }

    public Map<Position, Piece> getBoardMap() {
        return boardMap;
    }

    public void setBoardMap(Map<Position, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public Position getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(Position whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public Position getKing(boolean isWhite) {
        return isWhite ? whiteKingPosition : blackKingPosition;
    }

    public Position getBlackKingPosition() {
        return blackKingPosition;
    }

    public void setBlackKingPosition(Position blackKingPosition) {
        blackKingPosition = blackKingPosition;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            for (char letter = 'a'; letter <= 'h'; letter++) {
                //TODO: refactor this
                String l = boardMap.get(new Position(letter, i)) != null ? boardMap.get(new Position(letter, i)).toString() + " " : EMPTY_POSITION;
                stringBuilder.append(l);
                //  stringBuilder.append(' ');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Board clone() {
        return this.clone();
    }
}

package board;

import board.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board implements Cloneable {
    private Map<Position, Piece> boardMap = new HashMap<>();
    private static final String EMPTY_POSITION = ".  ";

    private static Position whiteKingPosition;
    private static Position blackKingPosition;

    public void addPosition(Position position) {
        addPosition(position, null);
    }

    public void addPosition(Position position, Piece piece) {
        boardMap.put(position, piece);

        if(piece == null)
        {
            return;
        }
        if (piece.isKing()) {
            if (piece.isWhite()) {
                whiteKingPosition = position;
            } else {
                blackKingPosition = position;
            }
        }
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

    public void movePiece(Position initialPosition, Position destinationPosition) {
        Piece movingPiece = boardMap.get(initialPosition);
        boardMap.put(destinationPosition, movingPiece);
        clearPosition(initialPosition);
    }

    private void clearPosition(Position position) {
        boardMap.put(position, null);
    }


    public Piece getPosition(Position position) {
        return boardMap.get(position);
    }

    public Position getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(Position whiteKingPosition) {
        Board.whiteKingPosition = whiteKingPosition;
    }

    public Position getKing(boolean isWhite) {
        return isWhite ? whiteKingPosition : blackKingPosition;
    }

    public static Position getBlackKingPosition() {
        return blackKingPosition;
    }

    public static void setBlackKingPosition(Position blackKingPosition) {
        Board.blackKingPosition = blackKingPosition;
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

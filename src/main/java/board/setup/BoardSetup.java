package board.setup;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.pieces.*;

import static board.PositionEnum.*;

//TODO: replace positions with enums (not worth the time)
public class BoardSetup
{

    private static final boolean WHITE = true;
    private static final boolean BLACK = false;

    public static String boardToFenNotation(Board board) throws Exception
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            for (char c = 'a'; c <= 'h'; c++) {
                Piece piece = board.getPieceAt(new Position(c, i));

                stringBuilder.append(piece != null ? piece.toFen() : " ");

            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static int boardScoreDifference(Board board)
    {
        int score = 0;
        for (Piece piece : board.getPieceMap(Board.myColorWhite).values()) {
            if(piece.getPieceType()!=PieceType.PAWN)
            score += piece.getScore();
        }

        for (Piece piece : board.getPieceMap(!Board.myColorWhite).values()) {
            if(piece.getPieceType()!=PieceType.PAWN)
            score -= piece.getScore();
        }
        return score;
    }

    public static void fenNotationBoardSetup(Board board, String fenNotation)
    {
        String[] array = fenNotation.split("/");
        int index1;
        for (int i = 8; i >= 2; i--) {
            index1 = 1;
            for (int j = 0; j < array[8 - i].length(); j++) {
                char c = array[8 - i].charAt(j);
                //is digit
                if ('0' <= c && c <= '9') {
                    index1 += c - '0';
                }
                else {
                    board.addPiece(positionFromIndexes(i, index1), pieceFromLetter(c));
                    index1++;
                }

            }
        }

        String lastRow = array[7];
        String[] lastRowArray = lastRow.split(" ");

        index1 = 1;
        for (int j = 0; j < lastRowArray[0].length(); j++) {
            char c = lastRowArray[0].charAt(j);
            //is digit
            if ('0' <= c && c <= '9') {
                index1 += c - '0';
            }
            else {
                board.addPiece(positionFromIndexes(1, index1), pieceFromLetter(c));
                index1++;
            }

            board.setWhiteToMove(lastRowArray[1].equals("w"));

        }

    }

    private static Position positionFromIndexes(int row, int collumn)
    {
        return new Position(collumn, row);
    }

    private static Piece pieceFromLetter(char c)
    {
        switch (c) {
            case 'P':
                return new Pawn(true);
            case 'N':
                return new Knight(true);
            case 'B':
                return new Bishop(true);
            case 'R':
                return new Rook(true);
            case 'Q':
                return new Queen(true);
            case 'K':
                return new King(true);
            case 'p':
                return new Pawn(false);
            case 'n':
                return new Knight(false);
            case 'b':
                return new Bishop(false);
            case 'r':
                return new Rook(false);
            case 'q':
                return new Queen(false);
            case 'k':
                return new King(false);
            default:
                return null;
        }
    }

    public static void setupBoard(Board board)
    {
        setPieces(board);
    }

    private static void setPieces(Board board)
    {
        addPawns(board);
        addKnights(board);
        addBishops(board);
        addRooks(board);
        addQueens(board);
        addKings(board);
    }

    private static void addPawns(Board board)
    {
        for (char letter = 'a'; letter <= 'h'; letter++) {
            board.addPiece(new Position(letter, 2), new Pawn(WHITE));
            board.addPiece(new Position(letter, 7), new Pawn(BLACK));
        }
    }

    private static void addKnights(Board board)
    {
        board.addPiece(B1.getPosition(), new Knight(WHITE));
        board.addPiece(G1.getPosition(), new Knight(WHITE));
        board.addPiece(B8.getPosition(), new Knight(BLACK));
        board.addPiece(G8.getPosition(), new Knight(BLACK));
    }

    private static void addBishops(Board board)
    {
        board.addPiece(new Position('c', 1), new Bishop(WHITE));
        board.addPiece(new Position('f', 1), new Bishop(WHITE));
        board.addPiece(new Position('c', 8), new Bishop(BLACK));
        board.addPiece(new Position('f', 8), new Bishop(BLACK));
    }

    private static void addRooks(Board board)
    {
        board.addPiece(new Position('a', 1), new Rook(WHITE));
        board.addPiece(new Position('h', 1), new Rook(WHITE));
        board.addPiece(new Position('a', 8), new Rook(BLACK));
        board.addPiece(new Position('h', 8), new Rook(BLACK));
    }

    private static void addQueens(Board board)
    {
        board.addPiece(new Position('d', 1), new Queen(WHITE));
        board.addPiece(new Position('d', 8), new Queen(BLACK));
    }

    private static void addKings(Board board)
    {
        board.addPiece(new Position('e', 1), new King(WHITE));
        board.addPiece(new Position('e', 8), new King(BLACK));
    }
}

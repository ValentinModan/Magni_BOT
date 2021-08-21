package board;

import board.moves.Move;
import board.moves.calculator.PossibleMovesCalculator;
import board.moves.controller.MoveController;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.GameBoard;
import game.kingcheck.attacked.KingSafety;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class OptimizedBoard
{
    private       List<Move>           possibleMoves  = new ArrayList<>();
    private final Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private final Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private final MoveController moveController = new MoveController();

    private static final String EMPTY_POSITION = ".  ";

    public static List<Move> allMoves = new ArrayList<>();

    public static List<Move> actualMoves = new ArrayList<>();

    private boolean isWhiteToMove = true;

    Position whiteKingPosition;

    Position blackKingPosition;

    public King getKing()
    {
        Piece piece = getPiece(getKingPosition(isWhiteToMove));
        return piece != null ? (King) piece : null;
    }

    public Position getKingPosition(boolean isWhite)
    {
        return isWhite ? whiteKingPosition : blackKingPosition;
    }

    public void actualMove(Move move)
    {
        actualMoves.add(move);
        move(move);
    }

    public void move(Move move)
    {
        moveController.move(this, move);
    }

    public boolean pieceExistsAt(Position position)
    {
        Piece piece = whitePiecesMap.get(position);
        if (piece != null) {
            return true;
        }
        piece = blackPiecesMap.get(position);
        return piece != null;
    }

    public Piece getPieceAt(Position position) throws Exception
    {
        Piece whitePiece = whitePiecesMap.get(position);
        Piece blackPiece = blackPiecesMap.get(position);
        if (whitePiece != null && blackPiece != null) {
            throw new Exception("Invalid board with two pieces at the same position: " + position + " " + allMoves);
        }
        if (whitePiece == null) {
            return blackPiece;
        }

        return whitePiece;
    }

    public int piecesLeft()
    {
        return whitePiecesMap.size() + blackPiecesMap.size();
    }

    public int newDepth()
    {
        int piecesLeft = piecesLeft();
        if (piecesLeft >= 16) {
            return GameBoard.DEPTH;
        }
        return GameBoard.DEPTH + (16 - piecesLeft) / 2 + 1;
    }

    public void updateKingPosition(Position position)
    {
        if (isWhiteToMove) {
            whiteKingPosition = position;
        }
        else {
            blackKingPosition = position;
        }
    }

    public void previousTurn()
    {
        nextTurn();
    }

    public void nextTurn()
    {
        isWhiteToMove = !isWhiteToMove;
    }

    public void setTurn(boolean isWhiteToMove)
    {
        this.isWhiteToMove = isWhiteToMove;
    }

    public List<Move> getPossibleMoves()
    {
        return possibleMoves;
    }

    public void undoMove(Move move)
    {
        moveController.undoMove(this, move);
    }

    public void addPiece(Position position, Piece piece)
    {
        if (piece.isWhite()) {
            if (piece.getPieceType() == PieceType.KING) {
                whiteKingPosition = position;
            }
            whitePiecesMap.put(position, piece);
        }
        else {
            if (piece.getPieceType() == PieceType.KING) {
                blackKingPosition = position;
            }
            blackPiecesMap.put(position, piece);
        }
    }

    public Piece getPiece(Position position)
    {
        if (isWhiteToMove) {
            return whitePiecesMap.get(position);
        }
        return blackPiecesMap.get(position);
    }

    public void computePossibleMoves()
    {
        //log.info("Compiling possible moves");
        possibleMoves = PossibleMovesCalculator.getPossibleMoves(this);
        possibleMoves = possibleMoves.stream().filter(move -> {
            move(move);
            int attackers = KingSafety.getNumberOfAttackers(this);
            undoMove(move);
            return attackers == 0;
        }).collect(Collectors.toList());
    }

    public Piece getMovingPiece(Position position)
    {
        return getMovingPiecesMap().get(position);
    }

    //TODO: rename method name
    public Map<Position, Piece> getMovingPiecesMap()
    {
        return isWhiteToMove ? whitePiecesMap : blackPiecesMap;
    }

    public Piece getTakenPiece(Position position)
    {
        return getTakenPiecesMap().get(position);
    }

    public Map<Position, Piece> getTakenPiecesMap()
    {
        return isWhiteToMove ? blackPiecesMap : whitePiecesMap;
    }

    public boolean isWhiteToMove()
    {
        return isWhiteToMove;
    }

    public void setWhiteToMove(boolean whiteToMove)
    {
        isWhiteToMove = whiteToMove;
    }

    public Move lastMove()
    {
        if (allMoves.size() == 0) {
            return null;
        }
        return allMoves.get(allMoves.size() - 1);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OptimizedBoard that = (OptimizedBoard) o;
        return whitePiecesMap.equals(that.whitePiecesMap) && blackPiecesMap.equals(that.blackPiecesMap);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(whitePiecesMap, blackPiecesMap);
    }

    public static void displayAllMoves()
    {
        for (Move move : actualMoves) {
            System.out.print(move.move() + " ");
        }
    }

    @SneakyThrows
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 8; i >= 1; i--) {
            for (char letter = 'a'; letter <= 'h'; letter++) {
                //TODO: refactor this
                Piece  piece = getPieceAt(new Position(letter, i));
                String l     = piece != null ? piece.toString() + "  " : EMPTY_POSITION;
                stringBuilder.append(l);
                //  stringBuilder.append(' ');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}



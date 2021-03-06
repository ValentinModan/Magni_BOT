package board;

import board.moves.movetypes.Move;
import board.moves.calculator.PossibleMovesCalculator;
import board.moves.service.MoveService;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.kingcheck.attacked.KingSafety;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Board implements Cloneable
{
    public static boolean myColorWhite = false;
    private List<Move> possibleMoves = new ArrayList<>();
    private Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private final MoveService moveService = new MoveService();

    private static final String EMPTY_POSITION = ".  ";

    public List<Move> allMoves = new ArrayList<>();

    public static List<Move> actualMoves = new ArrayList<>();

    private boolean isWhiteToMove = true;

    Position whiteKingPosition;

    Position blackKingPosition;

    //TODO: put singleton here
    @SneakyThrows
    public King getKing()
    {
        Piece piece = getPieceAt(getKingPosition());
        if (piece == null) {
            throw new NullPointerException("No king found at " + getKingPosition() + "\n" + this);
        }
        return (King) piece;
    }

    public Position getKingPosition()
    {
        return isWhiteToMove ? whiteKingPosition : blackKingPosition;
    }

    public void actualMove(Move move)
    {
        actualMoves.add(move);
        move(move);
    }

    public void move(Move move)
    {
        moveService.move(this, move);
    }

    //todo: use this at xray moves class?
    public boolean pieceExistsAt(Position position)
    {
        Piece piece = whitePiecesMap.get(position);
        if (piece != null) {
            return true;
        }
        piece = blackPiecesMap.get(position);
        return piece != null;
    }

    //TODO: this can be done with a strategy instead
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
        moveService.undoMove(this, move);
    }

    public void addPiece(Piece piece, Position... positions)
    {
        for (Position position : positions) {
            addPiece(position, piece);
        }
    }

    public void addPiece(Piece piece, PositionEnum... positionEnums)
    {
        for (PositionEnum positionEnum : positionEnums) {
            addPiece(positionEnum, piece);
        }
    }

    public void addPiece(PositionEnum positionEnum, Piece piece)
    {
        addPiece(positionEnum.getPosition(), piece);
    }

    public void addPiece(Position position, Piece piece)
    {
        Map<Position, Piece> pieceMap = piece.isWhite() ? whitePiecesMap : blackPiecesMap;

        pieceMap.put(position, piece);

        if (piece.getPieceType() == PieceType.KING) {
            if (piece.isWhite()) {
                whiteKingPosition = position;
            }
            else {
                blackKingPosition = position;
            }
        }
    }

    public Piece getPiece(Position position)
    {
        if (isWhiteToMove) {
            return whitePiecesMap.get(position);
        }
        return blackPiecesMap.get(position);
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

    //one of these must be removed
    public List<Move> calculatePossibleMoves()
    {
        computePossibleMoves();
        return getPossibleMoves();
    }

    //TODO: just remove check verification for performance improvement?
    public void computePossibleMoves()
    {
        possibleMoves = PossibleMovesCalculator.getPossibleMoves(this).stream()
                .filter(move ->
                        {
                            move(move);
                            boolean kingAttacked = KingSafety.isTheKingAttacked(this);
                            undoMove(move);
                            return !kingAttacked;
                        })
                .collect(Collectors.toList());
    }

    public Piece getMovingPiece(Position position)
    {
        return getMovingPiecesMap().get(position);
    }

    public Map<Position, Piece> getPieceMap(boolean isWhite)
    {
        return isWhite ? whitePiecesMap : blackPiecesMap;
    }

    //TODO: rename methods name
    public Map<Position, Piece> getMovingPiecesMap()
    {
        return isWhiteToMove ? whitePiecesMap : blackPiecesMap;
    }

    public Map<Position, Piece> getTakenPiecesMap()
    {
        return isWhiteToMove ? blackPiecesMap : whitePiecesMap;
    }

    //TODO: replace with enum?
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
        if (allMoves.isEmpty()) {
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
        return whitePiecesMap.entrySet().stream().allMatch(e -> whitePiecesMap.get(e) == (((Board) o).whitePiecesMap.get(e)))
                && blackPiecesMap.entrySet().stream().allMatch(e -> blackPiecesMap.get(e) == (((Board) o).blackPiecesMap.get(e)));
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

    public void setPossibleMoves(List<Move> possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public void setAllMoves(List<Move> allMoves)
    {
        this.allMoves = allMoves;
    }

    public void setMovingKingPosition(Position position)
    {
        if (isWhiteToMove) {
            setWhiteKingPosition(position);
        }
        else {
            setBlackKingPosition(position);
        }
    }

    public void setWhiteKingPosition(Position whiteKingPosition)
    {
        if (whiteKingPosition == null) {
            throw new NullPointerException("Something is really wrong\n" + this);
        }
        this.whiteKingPosition = whiteKingPosition;
    }

    public void setBlackKingPosition(Position blackKingPosition)
    {
        if (whiteKingPosition == null) {
            throw new NullPointerException("Something is really wrong\n" + this);
        }
        this.blackKingPosition = blackKingPosition;
    }

    public void setWhitePiecesMap(Map<Position, Piece> whitePiecesMap)
    {
        this.whitePiecesMap = whitePiecesMap;
    }

    public void setBlackPiecesMap(Map<Position, Piece> blackPiecesMap)
    {
        this.blackPiecesMap = blackPiecesMap;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Board newBoard = (Board) super.clone();

        newBoard.setWhiteToMove(isWhiteToMove);
        newBoard.setWhitePiecesMap(whitePiecesMap.entrySet().stream().filter(Objects::nonNull)
                                           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        newBoard.setBlackPiecesMap(blackPiecesMap.entrySet().stream().filter(Objects::nonNull)
                                           .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        newBoard.setBlackKingPosition(blackKingPosition);
        newBoard.setWhiteKingPosition(whiteKingPosition);
        newBoard.setAllMoves(new ArrayList<>(allMoves));

        return newBoard;
    }

    //    @SneakyThrows
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 8; i >= 1; i--) {
            for (char letter = 'a'; letter <= 'h'; letter++) {
                //TODO: refactor this
                Piece piece = null;
                try {
                    piece = getPieceAt(new Position(letter, i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String l = piece != null ? piece + "  " : EMPTY_POSITION;
                stringBuilder.append(l);
                //  stringBuilder.append(' ');
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(isWhiteToMove ? "White" : "Black").append(" to move.\n");
        stringBuilder.append("All moves are ").append(allMoves).append("\n");
        stringBuilder.append("Possible move are :").append(possibleMoves).append("\n");
        stringBuilder.append("White king position is:").append(whiteKingPosition).append("\n");
        stringBuilder.append("Black king position is:").append(blackKingPosition).append("\n");
        return stringBuilder.toString();
    }

}



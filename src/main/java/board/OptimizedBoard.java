package board;

import board.moves.Move;
import board.moves.calculator.PossibleMovesCalculator;
import board.moves.controller.MoveController;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.kingcheck.attacked.KingSafety;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class OptimizedBoard implements Cloneable
{
    private List<Move>           possibleMoves  = new ArrayList<>();
    private Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private final MoveController moveController = new MoveController();

    private static final String EMPTY_POSITION = ".  ";

    public List<Move> allMoves = new ArrayList<>();

    public static List<Move> actualMoves = new ArrayList<>();

    private boolean isWhiteToMove = true;

    Position whiteKingPosition;

    Position blackKingPosition;

    //TODO: put singleton here
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
        displayFullMoveLogic(move);
    }

    void displayFullMoveLogic(Move move)
    {
        StringBuilder sb          = new StringBuilder();
        Move          currentMove = move;
        while (currentMove != null) {
            sb.append(move).append(" with score ").append(move.getScore()).append(" |");
            currentMove = currentMove.getBestResponse();
        }

        log.info(sb.toString());
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

    public int myPiecesLeft()
    {
        return getTakenPiecesMap().size();
    }

    public int opponentPiecesLeft()
    {
        return getTakenPiecesMap().size();
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
        moveController.undoMove(this, move);
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

    //one of these must be removed
    public List<Move> calculatePossibleMoves()
    {
        return PossibleMovesCalculator.getPossibleMoves(this)
                .stream()
                .filter(move -> {     //filter moves after which the king is attacked
                    move(move);
                    int attackers = KingSafety.getNumberOfAttackers(this);
                    undoMove(move);
                    return attackers == 0;
                }).collect(Collectors.toList());
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

    //TODO: rename methods name
    public Map<Position, Piece> getMovingPiecesMap()
    {
        return isWhiteToMove ? whitePiecesMap : blackPiecesMap;
    }

    public Map<Position, Piece> getTakenPiecesMap()
    {
        return isWhiteToMove ? blackPiecesMap : whitePiecesMap;
    }

    //replace with enum?
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

    public void setPossibleMoves(List<Move> possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public Map<Position, Piece> getWhitePiecesMap()
    {
        return whitePiecesMap;
    }

    public List<Move> getAllMoves()
    {
        return allMoves;
    }

    public void setAllMoves(List<Move> allMoves)
    {
        this.allMoves = allMoves;
    }

    public static List<Move> getActualMoves()
    {
        return actualMoves;
    }


    public Position getWhiteKingPosition()
    {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(Position whiteKingPosition)
    {
        this.whiteKingPosition = whiteKingPosition;
    }

    public Position getBlackKingPosition()
    {
        return blackKingPosition;
    }

    public void setBlackKingPosition(Position blackKingPosition)
    {
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
        OptimizedBoard newBoard = (OptimizedBoard) super.clone();

        newBoard.setWhiteToMove(Boolean.valueOf(isWhiteToMove));
        newBoard.setWhitePiecesMap(new HashMap<>(whitePiecesMap));
        newBoard.setBlackPiecesMap(new HashMap<>(blackPiecesMap));
        newBoard.setBlackKingPosition(blackKingPosition);
        newBoard.setWhiteKingPosition(whiteKingPosition);
        newBoard.setAllMoves(new ArrayList<>(allMoves));

        return newBoard;
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



package board;

import board.moves.Move;
import board.moves.Movement;
import board.moves.calculator.PossibleMovesCalculator;
import board.pieces.King;
import board.pieces.Pawn;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.kingcheck.attacked.KingSafety;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class OptimizedBoard
{

    private       List<Move>           possibleMoves  = new ArrayList<>();
    private final Map<Position, Piece> whitePiecesMap = new HashMap<>();
    private final Map<Position, Piece> blackPiecesMap = new HashMap<>();

    private static final String EMPTY_POSITION = ".  ";

    private List<Move> allMoves = new ArrayList<>();

    private boolean isWhiteToMove = true;

    Position whiteKingPosition;

    Position blackKingPosition;

    public boolean isValidMove(Move move)
    {
        return possibleMoves.contains(move);
    }

    public King getKing()
    {
        return (King) getPiece(getKingPosition(isWhiteToMove));
    }

    public Position getKingPosition(boolean isWhite)
    {
        return isWhite ? whiteKingPosition : blackKingPosition;
    }

    public void move(Move move)
    {

        if (!isValidMove(move)) {
            //    log.warn("Warning, invalid move: " + move);
            //   System.out.println("Warning, invalid move!");
        }
        setMovingPiece(move);
        setTakenPiece(move);

        updateCastleMove(move);
        updateAnPassantMove(move);
        actualMove(move);
    }

    public void updateAnPassantMove(Move move)
    {

        Position initialPosition = move.getInitialPosition();
        Piece    takenPiece      = move.getTakenPiece();
        if (move.getMovingPiece() == null || move.getMovingPiece().getPieceType() != PieceType.PAWN) {
            return;
        }

        Pawn     pawn     = (Pawn) move.getMovingPiece();
        Movement movement = initialPosition.getDiagonal(move.getFinalPosition());
        Movement line     = movement.lineFromDiagonal();

        if (takenPiece != null) {
            return;
        }
        Position linePosition = initialPosition.move(line);

        Piece linePiece = getPiece(linePosition);
        if (linePiece == null || linePiece.getPieceType() != PieceType.PAWN) {
            return;
        }


        Move lastMove = lastMove();
        if (lastMove == null) {
            return;
        }
        //last pawn move was here
        if (lastMove.getFinalPosition().equals(linePosition)) {
            if (lastMove.getInitialPosition().equals(linePosition.move(Movement.upTwo(pawn.isWhite())))) {
                move.setAnPassant(true);
                move.setTakenAnPassant(linePosition);
            }

        }

    }

    private void updateCastleMove(Move move)
    {
        Piece movingPiece = move.getMovingPiece();
        Piece takenPiece  = move.getTakenPiece();

        if (takenPiece == null) {
            return;
        }
        if (movingPiece.getPieceType() == PieceType.KING) {
            if (takenPiece.getPieceType() == PieceType.ROOK) {
                if (movingPiece.isWhite() == movingPiece.isWhite()) {
                    move.setCastleMove(true);
                }
            }
        }
    }


    private void actualMove(Move move)
    {
        if (move.isCastleMove()) {
            castleMove(move);
        } else if (move.isAnPassant()) {
            anPassant(move);
        } else {
            //move moving piece
            getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());
            //clear original position
            getMovingPiecesMap().remove(move.getInitialPosition());

            //clear taken position
            getTakenPiecesMap().remove(move.getFinalPosition());


            if (move.getMovingPiece().getPieceType() == PieceType.KING) {
                if (isWhiteToMove) {
                    whiteKingPosition = move.getFinalPosition();
                } else {
                    blackKingPosition = move.getFinalPosition();
                }
            }
        }
        allMoves.add(move);
    }

    private void anPassant(Move move)
    {
        //add pawn to new position
        getMovingPiecesMap().put(move.getFinalPosition(), move.getMovingPiece());

        //remove taken pawn
        getTakenPiecesMap().remove(move.getTakenAnPassant());

        //remove initial position
        getMovingPiecesMap().remove(move.getInitialPosition());
    }

    private void castleMove(Move move)
    {
        Movement direction = move.getInitialPosition().castleDirection(move.getFinalPosition());

        Position kingInitial = move.getInitialPosition();
        Position rookInitial = move.getFinalPosition();

        Position kingFinal = kingInitial.move(direction).move(direction);
        Position rookFinal = kingFinal.move(direction.opposite());

        //move the king
        getMovingPiecesMap().put(kingFinal, move.getMovingPiece());
        getMovingPiecesMap().remove(kingInitial);


        //move rook
        getMovingPiecesMap().put(rookFinal, move.getTakenPiece());
        getMovingPiecesMap().remove(rookInitial);

        //update king position
        if (isWhiteToMove) {
            whiteKingPosition = kingFinal;
        } else {
            blackKingPosition = kingFinal;
        }
    }

    public Map<Position, Piece> getWhitePiecesMap()
    {
        return whitePiecesMap;
    }

    public Map<Position, Piece> getBlackPiecesMap()
    {
        return blackPiecesMap;
    }

    public List<Move> getPossibleMoves()
    {
        return possibleMoves;
    }

    public void undoMove(Move move)
    {
        if (whitePiecesMap.size() != 16 || blackPiecesMap.size() != 16) {
        }

        if (move.getMovingPiece().getPieceType() == PieceType.KING) {
            if (isWhiteToMove) {
                whiteKingPosition = move.getInitialPosition();
            } else {
                blackKingPosition = move.getInitialPosition();
            }
        }


        //put the piece back where it was
        getMovingPiecesMap().put(move.getInitialPosition(), move.getMovingPiece());
        //clear moved piece position
        getMovingPiecesMap().remove(move.getFinalPosition());

        Piece takenPiece = move.getTakenPiece();

        if (takenPiece != null) {
            getTakenPiecesMap().put(move.getFinalPosition(), move.getTakenPiece());
        }

        allMoves.remove(move);
    }

    public void addPiece(Position position, Piece piece)
    {
        if (piece.isWhite()) {
            if (piece.getPieceType() == PieceType.KING) {
                whiteKingPosition = position;
            }
            whitePiecesMap.put(position, piece);
        } else {
            if (piece.getPieceType() == PieceType.KING) {
                blackKingPosition = position;
            }
            blackPiecesMap.put(position, piece);
        }
    }

    public Piece getPiece(Position position)
    {
        Piece piece = whitePiecesMap.get(position);
        return piece != null ? piece : blackPiecesMap.get(position);
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


    //TODO: rename method name
    public Map<Position, Piece> getMovingPiecesMap()
    {
        return isWhiteToMove ? whitePiecesMap : blackPiecesMap;
    }

    private Map<Position, Piece> getTakenPiecesMap()
    {
        return isWhiteToMove ? blackPiecesMap : whitePiecesMap;
    }

    private void setMovingPiece(Move move)
    {
        //piece is already set
        if (move.getMovingPiece() != null)
            return;

        if (isWhiteToMove) {
            move.setMovingPiece(whitePiecesMap.get(move.getInitialPosition()));
            return;
        }
        move.setMovingPiece(blackPiecesMap.get(move.getInitialPosition()));
    }

    private void setTakenPiece(Move move)
    {
        if (move.getTakenPiece() != null)
            return;

        if (blackPiecesMap.get(move.getFinalPosition()) != null) {
            move.setTakenPiece(blackPiecesMap.get(move.getFinalPosition()));
            return;
        }
        move.setTakenPiece(whitePiecesMap.get(move.getFinalPosition()));
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
        if (allMoves.size() == 0)
            return null;
        return allMoves.get(allMoves.size() - 1);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptimizedBoard that = (OptimizedBoard) o;
        return whitePiecesMap.equals(that.whitePiecesMap) && blackPiecesMap.equals(that.blackPiecesMap);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(whitePiecesMap, blackPiecesMap);
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 8; i >= 1; i--) {
            for (char letter = 'a'; letter <= 'h'; letter++) {
                //TODO: refactor this
                Piece  piece = getPiece(new Position(letter, i));
                String l     = piece != null ? piece.toString() + "  " : EMPTY_POSITION;
                stringBuilder.append(l);
                //  stringBuilder.append(' ');
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}



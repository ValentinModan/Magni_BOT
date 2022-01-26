package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static board.moves.Movement.DOWN_TWO;
import static board.moves.Movement.UP_TWO;

public class PawnMoveCalculator extends PieceMoveCalculator
{
    private final int SECOND_ROW_WHITE = 2;
    private final int SECOND_ROW_BLACK = 7;
    private final int FINAL_ROW_WHITE  = 8;
    private final int FINAL_ROW_BLACK  = 1;

    @Override
    public List<Move> computeMoves(Board board, Position position)
    {
        List<Move>     moveList     = new ArrayList<>();
        Pawn           piece        = (Pawn) board.getMovingPiece(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);

        for (Movement movement : movementList) {
            moveList.addAll(moveCalculator(board, movement, position, piece));

        }
        return moveList;
    }

    private List<Move> moveCalculator(Board board, Movement movement, Position position, Pawn pawn)
    {
        List<Move> moveList            = new ArrayList<>();
        Position   destinationPosition = position.move(movement);
        Piece      takenPiece          = board.getTakenPiecesMap().get(destinationPosition);

        if (!destinationPosition.isValid()) {
            return Collections.emptyList();
        }
        switch (movement) {
            case UP:
            case DOWN:
                if (board.pieceExistsAt(destinationPosition)) {
                    return Collections.emptyList();
                }
                if (isPawnPromotion(pawn, destinationPosition)) {
                    moveList.addAll(allPromotions(position, destinationPosition, pawn.isWhite()));
                }
                else {
                    moveList.add(new Move(position, destinationPosition));
                }
                break;
            case UP_TWO:
            case DOWN_TWO:
                if (takenPiece != null) {
                    return Collections.emptyList();
                }
                //can not double jump over pieces
                if (movement == UP_TWO && (board.pieceExistsAt(position.move(Movement.UP))
                        || board.pieceExistsAt(position.move(UP_TWO)))) {
                    return Collections.emptyList();
                }
                if (movement == DOWN_TWO && board.pieceExistsAt(position.move(Movement.DOWN)) ||
                        board.pieceExistsAt(position.move(DOWN_TWO))) {
                    return Collections.emptyList();
                }
                if (canDoubleJump(pawn, position)) {
                    moveList.add(new Move(position, destinationPosition));
                }
                break;
            case UP_LEFT:
            case UP_RIGHT:
            case DOWN_RIGHT:
            case LEFT_DOWN:
                if (takenPiece == null) {

                    //moving an passant
                    Position linePosition        = position.move(movement.lineFromDiagonal());
                    Piece    anPassantTakenPiece = board.getTakenPiecesMap().get(linePosition);
                    if (anPassantTakenPiece != null && anPassantTakenPiece.getPieceType() == PieceType.PAWN) {
                        Move lastMove = board.lastMove();

                        if (lastMove == null) {
                            return Collections.emptyList();
                        }
                        //last pawn move was here
                        if (lastMove.getFinalPosition().equals(linePosition)) {
                            if (lastMove.getInitialPosition().equals(linePosition.move(Movement.upTwo(pawn.isWhite())))) {
                                Move move = new Move(position, destinationPosition);
                                move.setAnPassant(true);
                                move.setTakenAnPassant(linePosition);
                                moveList.add(move);
                                return moveList;
                            }
                        }
                    }
                    return Collections.emptyList();
                }
                if (pawn.isOpponentOf(takenPiece)) {
                    if (isPawnPromotion(pawn, destinationPosition)) {
                        moveList.addAll(allPromotions(position, destinationPosition, pawn.isWhite()));
                    }
                    else {
                        moveList.add(new Move(position, destinationPosition));
                    }
                }

            default:
                break;
        }

        return moveList;
    }

    private boolean canDoubleJump(Pawn pawn, Position position)
    {
        if (pawn.isWhite() && position.getRow() == SECOND_ROW_WHITE) {
            return true;
        }
        return !pawn.isWhite() && position.getRow() == SECOND_ROW_BLACK;
    }

    private boolean isPawnPromotion(Pawn Pawn, Position position)
    {
        if (Pawn.isWhite() && position.getRow() == FINAL_ROW_WHITE) {
            return true;
        }
        return !Pawn.isWhite() && position.getRow() == FINAL_ROW_BLACK;
    }

    private List<Move> allPromotions(Position initialPosition, Position destinationPosition, boolean isWhite)
    {
        if (!destinationPosition.isValid()) {
            return Collections.emptyList();
        }
        List<Move> moveList = new ArrayList<>();

        moveList.add(new Move(initialPosition, destinationPosition, new Rook(isWhite), true));
        moveList.add(new Move(initialPosition, destinationPosition, new Bishop(isWhite), true));
        moveList.add(new Move(initialPosition, destinationPosition, new Knight(isWhite), true));
        moveList.add(new Move(initialPosition, destinationPosition, new Queen(isWhite), true));
        return moveList;
    }
}

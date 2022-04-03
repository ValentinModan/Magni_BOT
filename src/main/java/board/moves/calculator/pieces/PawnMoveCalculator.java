package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.movetypes.Move;
import board.moves.Movement;
import board.moves.movetypes.PawnPromotionMove;
import board.moves.pieces.BlackPawnMovement;
import board.moves.pieces.WhitePawnMovement;
import board.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static board.moves.Movement.*;
import static board.pieces.PieceType.PAWN;

public class PawnMoveCalculator extends PieceMoveCalculator
{
    private static final PawnMoveCalculator pawnMoveCalculator = new PawnMoveCalculator();

    private PawnMoveCalculator()
    {
    }

    public static PawnMoveCalculator getInstance()
    {
        return pawnMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position)
    {
        List<Move> moveList = new ArrayList<>();
        Pawn piece = (Pawn) board.getMovingPiece(position);
        List<Movement> movementList = piece.isWhite() ?
                WhitePawnMovement.getInstance().getMovements() :
                BlackPawnMovement.getInstance().getMovements();

        for (Movement movement : movementList) {
            moveList.addAll(moveCalculator(board, movement, position, piece));
        }
        return moveList;
    }

    private List<Move> moveCalculator(Board board, Movement movement, Position initialPosition, Pawn pawn)
    {
        List<Move> moveList = new ArrayList<>();
        Position destinationPosition = initialPosition.move(movement);

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
                    moveList.addAll(allPromotions(initialPosition, destinationPosition, pawn.isWhite()));
                }
                else {
                    moveList.add(new Move(initialPosition, destinationPosition));
                }
                break;
            case UP_TWO:
            case DOWN_TWO:
                //can not double jump over pieces
                if (movement == UP_TWO && (
                        board.pieceExistsAt(initialPosition.move(UP)) || board.pieceExistsAt(initialPosition.move(UP_TWO)))) {
                    return Collections.emptyList();
                }
                if (movement == DOWN_TWO && (board.pieceExistsAt(initialPosition.move(DOWN)) ||
                        board.pieceExistsAt(initialPosition.move(DOWN_TWO)))) {
                    return Collections.emptyList();
                }
                if (canDoubleJump(pawn, initialPosition)) {
                    moveList.add(new Move(initialPosition, destinationPosition));
                }
                break;
            case UP_LEFT:
            case UP_RIGHT:
            case DOWN_RIGHT:
            case LEFT_DOWN:
                Piece takenPiece = board.getTakenPiecesMap().get(destinationPosition);
                if (pawn.isOpponentOf(takenPiece)) {
                    if (isPawnPromotion(pawn, destinationPosition)) {
                        moveList.addAll(allPromotions(initialPosition, destinationPosition, pawn.isWhite()));
                    }
                    else {
                        moveList.add(new Move(initialPosition, destinationPosition));
                    }
                    return moveList;
                }
                //moving en passant
                if (!board.pieceExistsAt(destinationPosition)) {
                    Position linePosition = initialPosition.move(movement.lineFromDiagonal());
                    Piece enPassantTakenPiece = board.getTakenPiecesMap().get(linePosition);
                    if (enPassantTakenPiece != null && enPassantTakenPiece.getPieceType() == PAWN) {
                        Move lastMove = board.lastMove();

                        //last move was the pawn pushed two squares
                        if (lastMove != null && lastMove.getFinalPosition().equals(linePosition)
                                && lastMove.getInitialPosition().equals(linePosition.move(Movement.upTwo(pawn.isWhite())))) {
                            moveList.add(new Move(initialPosition, destinationPosition, true, linePosition));
                            return moveList;
                        }

                    }
                }
                break;
            default:
                break;
        }

        return moveList;
    }

    private boolean canDoubleJump(Pawn pawn, Position position)
    {
        int SECOND_ROW_WHITE = 2;
        if (pawn.isWhite() && position.getRow() == SECOND_ROW_WHITE) {
            return true;
        }
        int SECOND_ROW_BLACK = 7;
        return !pawn.isWhite() && position.getRow() == SECOND_ROW_BLACK;
    }

    private boolean isPawnPromotion(Pawn Pawn, Position position)
    {
        int FINAL_ROW_WHITE = 8;
        if (Pawn.isWhite() && position.getRow() == FINAL_ROW_WHITE) {
            return true;
        }
        int FINAL_ROW_BLACK = 1;
        return !Pawn.isWhite() && position.getRow() == FINAL_ROW_BLACK;
    }

    private List<Move> allPromotions(Position initialPosition, Position destinationPosition, boolean isWhite)
    {
        if (!destinationPosition.isValid()) {
            return Collections.emptyList();
        }
        List<Move> moveList = new ArrayList<>();

        moveList.add(new PawnPromotionMove(initialPosition, destinationPosition, new Rook(isWhite)));
        moveList.add(new PawnPromotionMove(initialPosition, destinationPosition, new Bishop(isWhite)));
        moveList.add(new PawnPromotionMove(initialPosition, destinationPosition, new Knight(isWhite)));
        moveList.add(new PawnPromotionMove(initialPosition, destinationPosition, new Queen(isWhite)));
        return moveList;
    }
}

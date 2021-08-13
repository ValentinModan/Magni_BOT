package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.moves.pieces.PawnMovement;
import board.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static board.moves.Movement.DOWN_TWO;
import static board.moves.Movement.UP_TWO;

public class PawnMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Move>     moveList     = new ArrayList<>();
        Pawn           piece        = (Pawn) board.getPiece(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);


        for (Movement movement : movementList) {
            Position finalPosition    = position.move(movement);
            Piece    destinationPiece = board.getPiece(finalPosition);


            moveList.addAll(moveCalculator(board, movement, position, piece));

//            if (finalPosition.isValid() && (destinationPiece == null || destinationPiece.isWhite() != piece.isWhite())) {
//
//                //extra checks for attacking pawns
//                boolean isAttackPawnMovement = PawnMovement.attackMovements(piece.isWhite()).contains(movement);
//                if ((!isAttackPawnMovement && destinationPiece == null)//is not an attack pawn movement
//                        || ((destinationPiece != null && destinationPiece.isWhite() != piece.isWhite()))) {
//                    Move move = new Move(position, finalPosition);
//
//                    moveList.add(move);
//                }
//            }
        }
        return moveList;
    }

    private List<Move> moveCalculator(OptimizedBoard board, Movement movement, Position position, Pawn pawn)
    {
        List<Move> moveList            = new ArrayList<>();
        Position   destinationPosition = position.move(movement);
        Piece      takenPiece          = board.getPiece(destinationPosition);

        if (!destinationPosition.isValid()) {
            return Collections.emptyList();
        }
        switch (movement) {
            case UP:
            case DOWN:
                if (takenPiece != null) {
                    return Collections.emptyList();
                }
                if (isPawnPromotion(pawn, destinationPosition)) {
                    moveList.addAll(allPromotions(position, destinationPosition, pawn.isWhite()));
                } else {
                    moveList.add(new Move(position, destinationPosition));
                }
                break;
            case UP_TWO:
            case DOWN_TWO:
                if (takenPiece != null) {
                    return Collections.emptyList();
                }
                //can not double jump over pieces
                if (movement == UP_TWO && board.getPiece(position.move(Movement.UP)) != null) {
                    return Collections.emptyList();
                }
                if (movement == DOWN_TWO && board.getPiece(position.move(Movement.DOWN)) != null) {
                    return Collections.emptyList();
                }
                if (pawn.hasMoved()) {
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
                    return Collections.emptyList();
                }
                if (oppositeColorPieces(pawn, takenPiece)) {
                    if (isPawnPromotion(pawn, destinationPosition)) {
                        moveList.addAll(allPromotions(position, destinationPosition, pawn.isWhite()));
                    } else
                        moveList.add(new Move(position, destinationPosition));
                }

            default:
                break;
        }

        return moveList;
    }

    private boolean canDoubleJump(Pawn pawn, Position position)
    {
        if (pawn.isWhite() && position.getRow() == 2) {
            return true;
        }

        return !pawn.isWhite() && position.getRow() == 7;
    }

    private boolean isPawnPromotion(Pawn Pawn, Position position)
    {
        if (Pawn.isWhite() && position.getRow() == 8) {
            return true;
        }

        return !Pawn.isWhite() && position.getRow() == 1;
    }

    private List<Move> allPromotions(Position initialPosition, Position destinationPosition, boolean isWhite)
    {
        if (!destinationPosition.isValid()) {
            return Collections.emptyList();
        }
        List<Move> moveList = new ArrayList<>();

        moveList.add(new Move(initialPosition, destinationPosition, new Rook(isWhite), isWhite));
        moveList.add(new Move(initialPosition, destinationPosition, new Bishop(isWhite), isWhite));
        moveList.add(new Move(initialPosition, destinationPosition, new Knight(isWhite), isWhite));
        moveList.add(new Move(initialPosition, destinationPosition, new Queen(isWhite), isWhite));
        return moveList;
    }
}

package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.moves.pieces.PawnMovement;
import board.pieces.Pawn;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveCalculator extends PieceMoveCalculator {

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position) {
        List<Move> moveList = new ArrayList<>();
        Piece piece = board.getPiece(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);


        for (Movement movement : movementList) {
            Position finalPosition = position.move(movement);
            Piece destinationPiece = board.getPiece(finalPosition);
            if (finalPosition.isValid() && (destinationPiece == null || destinationPiece.isWhite() != piece.isWhite())) {

                //extra checks for attacking pawns
                boolean isAttackPawnMovement = PawnMovement.attackMovements(piece.isWhite()).contains(movement);
                if (!isAttackPawnMovement //is not an attack pawn movement
                        || (isAttackPawnMovement && (destinationPiece != null && destinationPiece.isWhite() != piece.isWhite()))) {
                    Move move = new Move(position, finalPosition);

                    moveList.add(move);
                }
            }
        }
        return moveList;
    }
}

package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class BishopMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Move>     moveList     = new ArrayList<>();
        Piece          piece        = board.getPiece(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);

        for (Movement movement : movementList) {
            Position finalPosition = position.move(movement);
            Piece    takenPiece    = board.getPiece(finalPosition);
            while (finalPosition.isValid() && (takenPiece == null
                    || oppositeColorPieces(piece,takenPiece))) {
                Move move = new Move(position, finalPosition);
                moveList.add(move);
                if (takenPiece != null) {
                    break;
                }
                finalPosition = finalPosition.move(movement);
                takenPiece = board.getPiece(finalPosition);
            }
        }
        return moveList;
    }
}

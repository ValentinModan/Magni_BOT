package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class PieceMoveCalculator
{
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        List<Move> moveList = new ArrayList<>();
        Piece movingPiece = board.getPieceAt(position);

        for (Movement movement : MovementCalculator.getPossibleMoves(movingPiece)) {
            Position finalPosition = position.move(movement);
            Piece destinationPiece = board.getPieceAt(finalPosition);
            if (finalPosition.isValid() && (destinationPiece == null || destinationPiece.isOpponentOf(movingPiece))) {
                moveList.add(new Move(position, finalPosition));
            }
        }
        return moveList;
    }
}

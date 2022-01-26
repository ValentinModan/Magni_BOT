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

    static Logger logger = Logger.getLogger(PieceMoveCalculator.class.getName());


    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        List<Move> moveList = new ArrayList<>();
        Piece      piece    = board.getPiece(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);

        for (Movement movement : movementList) {
            Position finalPosition    = position.move(movement);
            Piece    destinationPiece = board.getPiece(finalPosition);
            if (finalPosition.isValid() && (destinationPiece == null || destinationPiece.isOpponentOf(piece))) {
                Move move = new Move(position, finalPosition);
                moveList.add(move);
            }
        }

        return moveList;
    }
}

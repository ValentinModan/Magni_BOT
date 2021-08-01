package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static board.moves.Movement.UP;

public class PawnMoveCalculator extends PieceMoveCalculator {

    @Override
    public List<Move> computeMoves(Board board, Position position) {
        List<Move> pawnMoves = new ArrayList<>();


        //move forward
        Position upPosition = position.move(UP);
        Piece upPiece = board.getPosition(upPosition);
        if (upPiece == null) {
            pawnMoves.add(new Move(position, upPosition));
        }
        return pawnMoves;
    }
}

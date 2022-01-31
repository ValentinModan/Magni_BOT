package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class QueenMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        List<Movement> movementList = MovementCalculator.getPossibleMoves(board.getPieceAt(position));
        return Xray.xRayMoveList(board, position, movementList);
    }
}

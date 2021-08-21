package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Piece;
import game.kingcheck.attacked.Xray;

import java.util.ArrayList;
import java.util.List;

public class QueenMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position) throws Exception
    {
        List<Movement> movementList = MovementCalculator.getPossibleMoves(board.getPieceAt(position));

        return Xray.xRayMoveList(board, position, movementList);
    }
}

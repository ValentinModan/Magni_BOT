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

public class RookMoveCalculator extends PieceMoveCalculator
{
    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Move>     moveList     = new ArrayList<>();
        List<Movement> movementList = MovementCalculator.getPossibleMoves(board.getMovingPiecesMap().get(position));

        for (Movement movement : movementList) {
            Xray.xRayMoves(board, moveList, position, position, movement);
        }
        return moveList;
    }
}

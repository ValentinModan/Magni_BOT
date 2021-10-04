package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.piecees.Piece;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class BishopMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position) throws Exception
    {
        Piece          piece        = board.getPieceAt(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(piece);

        return Xray.xRayMoveList(board,position,movementList);
    }
}

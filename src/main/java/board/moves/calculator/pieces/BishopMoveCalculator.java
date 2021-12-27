package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Bishop;
import board.pieces.Piece;
import game.kingcheck.attacked.Xray;

import java.util.List;

public class BishopMoveCalculator extends PieceMoveCalculator
{

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        Bishop         bishop       = (Bishop) board.getPieceAt(position);
        List<Movement> movementList = MovementCalculator.getPossibleMoves(bishop);

        return Xray.xRayMoveList(board, position, movementList);
    }
}

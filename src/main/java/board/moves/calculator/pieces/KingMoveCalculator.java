package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.pieces.King;

import java.util.List;

public class KingMoveCalculator extends PieceMoveCalculator
{

    private static final Position WHITE_LEFT_ROOK_POSITION  = new Position('a', 1);
    private static final Position WHITE_RIGHT_ROOK_POSITION = new Position('h', 1);
    private static final Position BLACK_LEFT_ROOK_POSITION  = new Position('a', 8);
    private static final Position BLACK_RIGHT_ROOK_POSITION = new Position('h', 8);

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Move> moveList = super.computeMoves(board, position);


        King king = board.getKing();

        //implement castle

        return moveList;
    }


}

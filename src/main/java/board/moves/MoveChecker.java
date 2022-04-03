package board.moves;

import board.moves.movetypes.Move;

import static board.pieces.PieceType.KNIGHT;
import static board.pieces.PieceType.PAWN;

public class MoveChecker
{
    public static boolean moveKnightFromFirstRow(Move move)
    {
        return move.getMovingPiece().getPieceType() == KNIGHT &&
                (move.getInitialPosition().getRow() == 1 || move.getInitialPosition().getRow() == 8);
    }

    public static boolean isPawnMove(Move move)
    {
        return move.getMovingPiece().getPieceType() == PAWN;
    }
}

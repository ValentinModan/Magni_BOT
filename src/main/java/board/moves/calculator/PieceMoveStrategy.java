package board.moves.calculator;

import board.moves.calculator.pieces.*;
import board.pieces.PieceType;

public class PieceMoveStrategy
{
    public static PieceMoveCalculator getPieceCalculatorStrategy(PieceType pieceType) throws Exception
    {
        switch (pieceType)
        {
            case KING:
                return KingMoveCalculator.getInstance();
            case QUEEN:
                return QueenMoveCalculator.getInstance();
            case ROOK:
                return RookMoveCalculator.getInstance();
            case BISHOP:
                return BishopMoveCalculator.getInstance();
            case KNIGHT:
                return KnightMoveCalculator.getInstance();
            case PAWN:
                return PawnMoveCalculator.getInstance();
            default:
                throw new Exception("No such type " + pieceType);
        }
    }
}

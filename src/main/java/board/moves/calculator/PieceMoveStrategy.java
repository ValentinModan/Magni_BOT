package board.moves.calculator;

import board.moves.calculator.pieces.*;
import board.pieces.Piece;
import board.pieces.PieceType;

public class PieceMoveStrategy
{
    public static PieceMoveCalculator generatePieceCalculatorStrategy(PieceType pieceType)
    {
        switch (pieceType)
        {
            case KING:
                return new KingMoveCalculator();
            case QUEEN:
                return new QueenMoveCalculator();
            case ROOK:
                return new RookMoveCalculator();
            case BISHOP:
                return new BishopMoveCalculator();
            case KNIGHT:
                return new KnightMoveCalculator();
            case PAWN:
                return new PawnMoveCalculator();
            default:
                return null;
        }
    }
}

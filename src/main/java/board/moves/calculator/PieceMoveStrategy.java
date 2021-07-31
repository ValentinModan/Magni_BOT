package board.moves.calculator;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.*;
import board.pieces.Piece;

import java.util.List;

public class PieceMoveStrategy {


    public static PieceMoveCalculator getCalculator(Piece piece)
    {
        if(piece.isKing())
        {
            return new KingMoveCalculator();
        }
        if(piece.isQueen())
        {
            return new QueenMoveCalculator();
        }
        if(piece.isRook())
        {
            return new RookMoveCalculator();
        }
        if(piece.isBishop())
        {
            return new BishopMoveCalculator();
        }
        if(piece.isKnight())
        {
            return new KnightMoveCalculator();
        }
        if(piece.isPawn())
        {
            return new PawnMoveCalculator();
        }
        return null;
    }
}

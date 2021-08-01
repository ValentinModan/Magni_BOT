package board.moves.calculator;

import board.Board;
import board.Position;
import board.moves.Move;
import board.moves.calculator.pieces.*;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class PieceMoveStrategy {


    public static PieceMoveCalculator getCalculator(Piece piece)
    {
        if(piece.getPieceType() == PieceType.KING)
        {
            return new KingMoveCalculator();
        }
        if(piece.getPieceType() == PieceType.QUEEN)
        {
            return new QueenMoveCalculator();
        }
        if(piece.getPieceType() == PieceType.ROOK)
        {
            return new RookMoveCalculator();
        }
        if(piece.getPieceType() == PieceType.BISHOP)
        {
            return new BishopMoveCalculator();
        }
        if(piece.getPieceType() == PieceType.KNIGHT)
        {
            return new KnightMoveCalculator();
        }
        if(piece.getPieceType() == PieceType.PAWN)
        {
            return new PawnMoveCalculator();
        }
        return null;
    }
}

package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class MovementCalculator
{
    public static List<Movement> getPossibleMoves(Piece piece)
    {
        PieceMovement pieceMovement;

        switch (piece.getPieceType()) {
            case PAWN:
                pieceMovement = piece.isWhite() ? WhitePawnMovement.getInstance() : BlackPawnMovement.getInstance();
                break;
            case ROOK:
                pieceMovement = RookMovement.getInstance();
                break;
            case KING:
                pieceMovement = KingMovement.getInstance();
                break;
            case QUEEN:
                pieceMovement = QueenMovement.getInstance();
                break;
            case KNIGHT:
                pieceMovement = KnightMovement.getInstance();
                break;
            case BISHOP:
                pieceMovement = BishopMovement.getInstance();
                break;
            default:
                throw new IllegalStateException("Unknown piece type: " + piece.getPieceType());
        }
        return pieceMovement.getMovements();

    }
}

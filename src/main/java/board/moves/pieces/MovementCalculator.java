package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class MovementCalculator {

    public static List<Movement> getPossibleMoves(Piece piece)
    {
        PieceMovement pieceMovement;

        switch (piece.getPieceType())
        {
            case PAWN:
                pieceMovement = new PawnMovement();
            case ROOK:
                pieceMovement = new RookMovement();
            case KING:
                pieceMovement = new KingMovement();
            case QUEEN:
                pieceMovement = new QueenMovement();
            case KNIGHT:
                pieceMovement = new KnightMovement();
            case BISHOP:
                pieceMovement = new BishopMovement();
                break;
            default:
                throw new IllegalStateException("Unknown piece type: " + piece.getPieceType());
        }
        return pieceMovement.getMovements(piece);

    }
}

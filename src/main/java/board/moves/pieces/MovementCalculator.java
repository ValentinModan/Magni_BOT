package board.moves.pieces;

import board.moves.Movement;
import board.pieces.Piece;

import java.util.List;

public class MovementCalculator {

    public static List<Movement> getPossibleMoves(Piece piece)
    {
        PieceMovement pieceMovement;

        switch (piece.getPieceType())
        {
            case PAWN:
                pieceMovement = new PawnMovement();
                break;
            case ROOK:
                pieceMovement = new RookMovement();
                break;
            case KING:
                pieceMovement = new KingMovement();
                break;
            case QUEEN:
                pieceMovement = new QueenMovement();
                break;
            case KNIGHT:
                pieceMovement = new KnightMovement();
                break;
            case BISHOP:
                pieceMovement = new BishopMovement();
                break;
            default:
                throw new IllegalStateException("Unknown piece type: " + piece.getPieceType());
        }
        return pieceMovement.getMovements(piece);

    }
}

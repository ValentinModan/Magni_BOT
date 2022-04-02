package board.moves.movetypes;

import board.Position;
import board.pieces.Piece;

public class PawnPromotionMove extends Move
{
    public PawnPromotionMove(Position initialPosition, Position finalPosition, Piece movingPiece, boolean isPawnPromotion)
    {
        super(initialPosition, finalPosition, movingPiece, isPawnPromotion);
    }
}

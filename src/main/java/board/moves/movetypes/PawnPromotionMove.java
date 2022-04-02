package board.moves.movetypes;

import board.Position;
import board.pieces.Piece;

public class PawnPromotionMove extends Move
{
    private Piece promotionPiece;

    public PawnPromotionMove(Position initialPosition, Position finalPosition, Piece movingPiece)
    {
        super(initialPosition, finalPosition, movingPiece, true);
        promotionPiece = movingPiece;
    }

    @Override
    public Piece getPromotionPiece()
    {
        return promotionPiece;
    }

    public void setPromotionPiece(Piece promotionPiece)
    {
        this.promotionPiece = promotionPiece;
    }

    @Override
    public boolean isPawnPromotion()
    {
        return true;
    }
}

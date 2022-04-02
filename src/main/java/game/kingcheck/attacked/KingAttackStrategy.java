package game.kingcheck.attacked;

import board.Board;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.KingMovement;
import board.moves.pieces.MovementCalculator;
import board.pieces.Piece;

import java.util.List;

import static board.pieces.PieceType.KING;

//Makes sure not to make illegal move
public class KingAttackStrategy implements AttackStrategy
{
    //TODO: just check the enemy king position if it's distance is more than 1
    @Override
    public boolean isAttackingTheKing(Board board)
    {
        Position kingPosition = board.getKingPosition();

        for (Movement movement : KingMovement.getInstance().getMovements()) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null && piece.getPieceType() == KING && piece.isWhite() != board.isWhiteToMove()) {
                return true;
            }
        }
        return false;
    }
}

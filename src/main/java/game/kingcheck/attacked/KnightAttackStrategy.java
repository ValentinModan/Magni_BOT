package game.kingcheck.attacked;

import board.Board;
import board.BoardHelper;
import board.Position;
import board.moves.Movement;
import board.moves.pieces.KnightMovement;
import board.pieces.King;
import board.pieces.Piece;

import static board.BoardHelper.opponentHas;
import static board.pieces.PieceType.KNIGHT;

public class KnightAttackStrategy implements AttackStrategy
{

    @Override
    public boolean isAttackingTheKing(Board board)
    {
        if (!opponentHas(board, KNIGHT)) {
            return false;
        }
        Position kingPosition = board.getKingPosition();
        King king = board.getKing();

        for (Movement movement : KnightMovement.getInstance().getMovements()) {
            Piece piece = board.getTakenPiecesMap().get(kingPosition.move(movement));
            if (piece != null &&
                    piece.getPieceType() == KNIGHT &&
                    piece.isOpponentOf(king)) {
                return true;
            }
        }
        return false;
    }
}

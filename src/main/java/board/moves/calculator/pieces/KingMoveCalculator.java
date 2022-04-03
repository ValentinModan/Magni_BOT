package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.moves.movetypes.Move;
import board.moves.Movement;
import board.pieces.King;
import board.pieces.Piece;
import game.kingcheck.attacked.KingSafety;

import java.util.List;

import static board.PositionEnum.*;
import static board.moves.Movement.LEFT;
import static board.moves.Movement.RIGHT;
import static board.pieces.PieceType.ROOK;

public class KingMoveCalculator extends PieceMoveCalculator
{

    private static final KingMoveCalculator kingMoveCalculator = new KingMoveCalculator();

    private KingMoveCalculator()
    {
    }

    public static KingMoveCalculator getInstance()
    {
        return kingMoveCalculator;
    }

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        List<Move> moveList = super.computeMoves(board, position);

        if (canSideCastle((Board) board.clone(), LEFT)) {
            Move move;
            if (board.isWhiteToMove()) {
                move = new Move(INITIAL_WHITE_KING_POSITION, WHITE_ROOK_LEFT_INITIAL_POSITION);
            }
            else {
                move = new Move(INITIAL_BLACK_KING_POSITION, BLACK_ROOK_LEFT_INITIAL_POSITION);
            }
            move.setCastleMove(true);
            moveList.add(move);
        }

        if (canSideCastle((Board) board.clone(), RIGHT)) {
            Move move;
            if (board.isWhiteToMove()) {
                move = new Move(INITIAL_WHITE_KING_POSITION, PositionEnum.WHITE_ROOK_RIGHT_INITIAL_POSITION);
            }
            else {
                move = new Move(INITIAL_BLACK_KING_POSITION, PositionEnum.BLACK_ROOK_RIGHT_INITIAL_POSITION);
            }
            move.setCastleMove(true);
            moveList.add(move);
        }
        return moveList;
    }

    boolean isInitialPosition(Position position, King king)
    {
        if (king.isWhite()) {
            return position.equals(INITIAL_WHITE_KING_POSITION.getPosition());
        }
        return position.equals(INITIAL_BLACK_KING_POSITION.getPosition());
    }

    Position initalRookPosition(Movement direction, boolean isWhiteToMove)
    {
        if (direction == LEFT) {
            return isWhiteToMove ? WHITE_ROOK_LEFT_INITIAL_POSITION.getPosition() : BLACK_ROOK_LEFT_INITIAL_POSITION.getPosition();
        }
        return isWhiteToMove ? WHITE_ROOK_RIGHT_INITIAL_POSITION.getPosition() : BLACK_ROOK_RIGHT_INITIAL_POSITION.getPosition();
    }

    boolean canSideCastle(Board board, Movement direction) throws Exception
    {
        Position initialKingPosition = board.getKingPosition();
        Position initalRookPosition = initalRookPosition(direction, board.isWhiteToMove());
        Piece pieceAtLeftRookInitialPosition = board.getMovingPiece(initalRookPosition);

        //king is attacked or has moved
        if (!isInitialPosition(board.getKingPosition(), board.getKing())) {
            return false;
        }
        //no rook on side
        if (pieceAtLeftRookInitialPosition == null || pieceAtLeftRookInitialPosition.getPieceType() != ROOK) {
            return false;
        }

        //empty space moving the king left/right one time
        if (board.getPieceAt(board.getKingPosition().move(direction)) != null) {
            return false;
        }

        //empty space moving the king left/right two times
        if (board.getPieceAt(board.getKingPosition().move(direction).move(direction)) != null) {
            return false;
        }

        //empty space moving the king left/right three times, only for left side castle
        if (direction == LEFT && board.getPieceAt(board.getKingPosition().move(direction).move(direction).move(direction)) != null) {
            return false;
        }
        if (pieceHasMoved(board.allMoves, initialKingPosition)) {
            return false;
        }
        if (pieceHasMoved(board.allMoves, initalRookPosition)) {
            return false;
        }

        if (KingSafety.isTheKingAttacked(board)) {
            return false;
        }

        moveKingPosition(board, board.getKingPosition().move(direction));

        if (KingSafety.isTheKingAttacked(board)) {
            moveKingPosition(board, initialKingPosition);
            return false;
        }

        moveKingPosition(board, board.getKingPosition().move(direction));
        if (KingSafety.isTheKingAttacked(board)) {
            moveKingPosition(board, initialKingPosition);
            return false;
        }
        //to do check if I can remove this
        moveKingPosition(board, initialKingPosition);
        return true;
    }

    void moveKingPosition(Board board, Position newPosition)
    {
        board.getMovingPiecesMap().put(newPosition, board.getKing());
        board.getMovingPiecesMap().remove(board.getKingPosition());
        board.updateKingPosition(newPosition);
    }

    boolean pieceHasMoved(List<Move> moveList, Position position)
    {
        for (Move move : moveList) {
            if (move.toString().contains(position.toString())) {
                return true;
            }
        }
        return false;
    }


}

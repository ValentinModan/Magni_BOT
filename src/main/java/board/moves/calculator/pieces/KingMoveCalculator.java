package board.moves.calculator.pieces;

import board.Board;
import board.Position;
import board.PositionEnum;
import board.moves.Move;
import board.moves.Movement;
import board.pieces.King;
import board.pieces.Piece;
import game.kingcheck.attacked.KingSafety;

import java.util.List;

import static board.moves.Movement.LEFT;
import static board.moves.Movement.RIGHT;
import static board.pieces.PieceType.ROOK;

public class KingMoveCalculator extends PieceMoveCalculator
{

    //maybe will be used for castling
    private static final Position WHITE_LEFT_ROOK_POSITION = new Position('a', 1);
    private static final Position WHITE_RIGHT_ROOK_POSITION = new Position('h', 1);
    private static final Position BLACK_LEFT_ROOK_POSITION = new Position('a', 8);
    private static final Position BLACK_RIGHT_ROOK_POSITION = new Position('h', 8);

    @Override
    public List<Move> computeMoves(Board board, Position position) throws Exception
    {
        List<Move> moveList = super.computeMoves(board, position);

        if (canSideCastle((Board) board.clone(), LEFT)) {
            Move move;
            if (board.isWhiteToMove()) {
                move = new Move(PositionEnum.INITIAL_WHITE_KING_POSITION.getPosition(), PositionEnum.WHITE_ROOK_LEFT_INITIAL_POSITION.getPosition());
            }
            else {
                move = new Move(PositionEnum.INITIAL_BLACK_KING_POSITION.getPosition(), PositionEnum.BLACK_ROOK_LEFT_INITIAL_POSITION.getPosition());
            }
            move.setCastleMove(true);
            moveList.add(move);
        }

        if (canSideCastle((Board) board.clone(), RIGHT)) {
            Move move;
            if (board.isWhiteToMove()) {
                move = new Move(PositionEnum.INITIAL_WHITE_KING_POSITION.getPosition(), PositionEnum.WHITE_ROOK_RIGHT_INITIAL_POSITION.getPosition());
            }
            else {
                move = new Move(PositionEnum.INITIAL_BLACK_KING_POSITION.getPosition(), PositionEnum.BLACK_ROOK_RIGHT_INITIAL_POSITION.getPosition());
            }
            move.setCastleMove(true);
            moveList.add(move);
        }
        return moveList;
    }

    boolean isInitialPosition(Position position, King king)
    {
        if (king.isWhite()) {
            return position.equals(PositionEnum.INITIAL_WHITE_KING_POSITION.getPosition());
        }
        return position.equals(PositionEnum.INITIAL_BLACK_KING_POSITION.getPosition());
    }

    Position initalRookPosition(Movement direction, boolean isWhiteToMove)
    {
        if (direction == LEFT) {
            return isWhiteToMove ? WHITE_LEFT_ROOK_POSITION : BLACK_LEFT_ROOK_POSITION;
        }
        return isWhiteToMove ? WHITE_RIGHT_ROOK_POSITION : BLACK_RIGHT_ROOK_POSITION;
    }

    boolean canSideCastle(Board board, Movement direction) throws Exception
    {
        Position initialKingPosition = board.getKingPosition();
        Position INITIAL_ROOK_POSITION = initalRookPosition(direction, board.isWhiteToMove());
        Piece pieceAtLeftRookInitialPosition = board.getMovingPiece(INITIAL_ROOK_POSITION);

        //king is attacked or has moved
        if (!isInitialPosition(board.getKingPosition(), board.getKing())) {
            return false;
        }
        //no rook on side
        if (pieceAtLeftRookInitialPosition == null || pieceAtLeftRookInitialPosition.getPieceType() != ROOK) {
            return false;
        }

        //no empty space on left side
        if (board.getPieceAt(board.getKingPosition().move(direction)) != null) {
            return false;
        }

        //no empty space on double left side
        if (board.getPieceAt(board.getKingPosition().move(direction).move(direction)) != null) {
            return false;
        }

        //no empty space on triple left side
        if (direction == LEFT && board.getPieceAt(board.getKingPosition().move(direction).move(direction).move(direction)) != null) {
            return false;
        }

        if (pieceHasMoved(board.allMoves, initialKingPosition)) {
            return false;
        }

        if (pieceHasMoved(board.allMoves, INITIAL_ROOK_POSITION)) {
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
        moveKingPosition(board, initialKingPosition);
        return true;
    }

    void moveKingPosition(Board board, Position newPosition)
    {
        board.getMovingPiecesMap().put(newPosition, board.getKing());
        board.getMovingPiecesMap().put(board.getKingPosition(), null);
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

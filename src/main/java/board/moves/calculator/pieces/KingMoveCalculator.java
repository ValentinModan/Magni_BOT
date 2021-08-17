package board.moves.calculator.pieces;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.moves.Movement;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.List;

public class KingMoveCalculator extends PieceMoveCalculator {

    private static final Position WHITE_LEFT_ROOK_POSITION = new Position('a',1);
    private static final Position WHITE_RIGHT_ROOK_POSITION = new Position('h',1);
    private static final Position BLACK_LEFT_ROOK_POSITION = new Position('a',8);
    private static final Position BLACK_RIGHT_ROOK_POSITION = new Position('h',8);

    @Override
    public List<Move> computeMoves(OptimizedBoard board, Position position)
    {
        List<Move> moveList = super.computeMoves(board,position);

      //  Move castle = leftCastle(board, position);

      //  if(castle!=null)
        {
        //    moveList.add(castle);
        }
        //whiteCastle

        //blackCastle



        return  moveList;
    }

    public Move leftCastle(OptimizedBoard board, Position position)
    {
        King king = (King)board.getPiece(position);

        if(king.isWhite())
        {
            if(!king.hasMoved())
            {
                Piece piece = board.getPiece(WHITE_LEFT_ROOK_POSITION);

                if(piece.getPieceType()== PieceType.ROOK)
                {
                    if(board.getPiece(WHITE_LEFT_ROOK_POSITION.move(Movement.LEFT))==null)
                    {
                        if(board.getPiece(WHITE_LEFT_ROOK_POSITION.move(Movement.LEFT).move(Movement.LEFT))==null)
                        {
                            return null;
                        }
                    }
                }

            }
        }
        else
        {

        }

        return null;
    }

}

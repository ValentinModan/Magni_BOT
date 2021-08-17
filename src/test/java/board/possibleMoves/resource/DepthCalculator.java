package board.possibleMoves.resource;

import board.OptimizedBoard;
import board.Position;
import board.moves.Move;
import board.pieces.King;
import board.pieces.Piece;
import board.pieces.PieceType;
import game.kingcheck.attacked.KingSafety;

import java.util.List;

public class DepthCalculator
{

    public static int possibleMoves(OptimizedBoard board, int depth)
    {
        if (depth == 0) {
            return 1;
        }

        int moves = 0;

        board.computePossibleMoves();

        List<Move> moveList = board.getPossibleMoves();

        for (Move move : moveList) {
            if(depth==6)
            {
                System.out.println(moves);
            }

            board.move(move);

            Piece queen = board.getPiece(new Position('h',5));
            if(queen!=null && queen.getPieceType()== PieceType.QUEEN)
            {
                Piece pawn = board.getPiece(new Position('f',6));
                /*if(pawn!=null && pawn.getPieceType()==PieceType.PAWN)
                {
                    if(!move.getInitialPosition().equals(new Position('f',7)))
                    {
                        if(depth==1) {
                            if(KingSafety.getNumberOfAttackers(board)==0)
                            {
                                System.out.println(board);
                                if(KingSafety.getNumberOfAttackers(board)==0)
                                {
                                    System.out.println(board);

                                }
                            }
                        }
                    }
                }*/
            }
            if (KingSafety.getNumberOfAttackers(board) == 0) {
                board.setWhiteToMove(!board.isWhiteToMove());
                moves += possibleMoves(board, depth - 1);
                board.setWhiteToMove(!board.isWhiteToMove());
            }
            board.undoMove(move);
        }
        return moves;
    }
}

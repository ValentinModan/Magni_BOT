package game.gameSetupOptions;

import board.moves.Move;
import board.pieces.Piece;
import board.pieces.PieceType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOptions
{
    public static final int CHECK_MATE_SCORE = -1000;
    //TODO: RETHINK STALEMATE SCORE SYSTEM
    public static final int STALE_MATE_SCORE = 0;

    public static final int MINIMUM_MOVES = 10;
    public static final int MAXIMUM_MOVES = 40;

    public static Move checkMate(int depth)
    {
        return new Move(CHECK_MATE_SCORE*depth);
    }
    public static Move checkMate()
    {
        return new Move(CHECK_MATE_SCORE);
    }

    public static Move staleMate()
    {
        return new Move(STALE_MATE_SCORE);
    }

    public static List<Move> extractMoves(List<Move> moveList, int currentDepth)
    {
        int movesPercentage = percentageFromDepth(currentDepth);
        int moves           = movesFromDepth(currentDepth);
        if (movesPercentage < 80) {
            Collections.shuffle(moveList);
        }

        int movesNumber = Math.max(MINIMUM_MOVES, moves);

        movesNumber = Math.min(movesNumber, MAXIMUM_MOVES);

        movesNumber = Math.min(movesNumber, moveList.size());
        return new ArrayList<>(moveList.subList(0, movesNumber));
    }

    private static int movesFromDepth(int depth)
    {
        //TODO:replace with pairs
        if(depth<=2)
        {
            return 100;
        }
        if (depth <= 4) {
            return 100;
        }
        if (depth <= 6) {
            return 80;
        }
        return 10;
    }

    private static int percentageFromDepth(int currentDepth)
    {
        if (currentDepth <= 5) {
            return 100;
        }
        if (currentDepth <= 6) {
            return 100;
        }
        if (currentDepth <= 8) {
            return 20;
        }
        if (currentDepth <= 10) {
            return 10;
        }
        return 5;
    }

    public static int updateMoveScore(Move move)
    {

        Piece movingPiece = move.getMovingPiece();
        if(movingPiece==null)
        {
            return  move.getScore();
        }

        int score = move.getScore() * 10;
        if (movingPiece.getPieceType() == PieceType.KING) {
            score -= 2;
        }

        if (movingPiece.getPieceType() == PieceType.PAWN) {
            score += 1;
        }

        return score;
    }
}

package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.gameSetupOptions.GameOptions;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class CleanMoveCalculator
{
    public static Move calculate2(OptimizedBoard optimizedBoard, int depth)
    {
        final int currentDepth;
        if(optimizedBoard.lastMove()!=null && optimizedBoard.lastMove().getTakenPiece()!=null)
        {
            currentDepth = depth;
        }
        else
        {
            currentDepth = depth;
        }
        optimizedBoard.computePossibleMoves();
        List<Move> moveList      = GameOptions.extractMoves(optimizedBoard.getPossibleMoves(), depth);
        boolean    isWhiteToMove = optimizedBoard.isWhiteToMove();

        //stalemate or checkmate
        if (moveList.size() == 0) {
            if (optimizedBoard.lastMove().isCheckMate()) {
                return GameOptions.checkMate();
            }
            return GameOptions.staleMate();
        }
        //forcedMove
        if (isForcedMove(moveList)) {
            return moveList.get(0);
        }
        if (currentDepth == 1) {
            return moveList.stream().peek(move -> MoveUpdateHelper.moveUpdate(optimizedBoard, move))
                    .max(Comparator.comparing(Move::getScore))
                    .orElseThrow(NoSuchElementException::new);
        }
        return moveList.stream().peek(move -> {
            makeMove(optimizedBoard, move);
            Move bestResponse = calculate2(optimizedBoard, currentDepth - 1);
            undoMove(optimizedBoard, move, isWhiteToMove);
            updateMoveWithResponse(move, bestResponse);
        }).max(Comparator.comparing(Move::getScore))
                .orElseThrow(NoSuchElementException::new);
    }

    private static void updateMoveWithResponse(Move move, Move response)
    {
        move.setScore(move.getScore() - response.getScore());
    }

    private static void makeMove(OptimizedBoard optimizedBoard, Move move)
    {
        MoveUpdateHelper.moveUpdate(optimizedBoard, move);
        optimizedBoard.move(move);
        optimizedBoard.nextTurn();
    }

    private static void undoMove(OptimizedBoard optimizedBoard, Move move, boolean isWhiteToMove)
    {
        optimizedBoard.setTurn(isWhiteToMove);
        optimizedBoard.undoMove(move);
    }

    private static boolean isForcedMove(List<Move> moveList)
    {
        return moveList.size() == 1;
    }
}

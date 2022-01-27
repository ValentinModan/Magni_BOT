package toDelete;

import board.Board;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.GameBoard;
import game.gameSetupOptions.GameOptions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class MultiThreadBoard
{
    private static final int          THREAD_COUNT = 8;
    private static final List<Worker> workerList   = new ArrayList<>();
    boolean firstTime = true;
    private Semaphore allDone;


    public void compute(Board board) throws CloneNotSupportedException
    {
        board.computePossibleMoves();
        if (firstTime) {
            setup(board);
        }
        allDone = new Semaphore(0);
        for (int i = 0; i < THREAD_COUNT; i++) {
            workerList.get(i).setOptimizedBoard((Board) board.clone());
            workerList.get(i).run();
        }


        // Wait to finish (this strategy is an alternative to join())
        try {
            System.out.println("Waiting for threads to finish");
            allDone.acquire(THREAD_COUNT);
            System.out.println("Threads finished");
            // Note: here use acquire(N) ..
            // could instead init semaphore with -9 and use
            // regular acquire() here
        } catch (InterruptedException ignored) {
        }

        for (Worker worker : workerList) {
            System.out.println("Best Move is " + worker.getBestMove());
        }


    }

    private void setup(Board board) throws CloneNotSupportedException
    {
        int workersNumber       = THREAD_COUNT;
        int possibleMovesLength = board.getPossibleMoves().size();

        int lenOneWorker = possibleMovesLength / workersNumber;
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * lenOneWorker;
            int end   = (i + 1) * lenOneWorker;

            if (i == workersNumber - 1) {
                end = possibleMovesLength;
            }
            Worker worker = new Worker((Board) board.clone(), start, end);
            workerList.add(worker);
            worker.start();
            firstTime = false;
        }
    }

    private class Worker extends Thread
    {

        int start, end;
        Board board;
        Move  bestMove;

        public Worker(Board board, int start, int end)
        {
            this.board = board;
            this.start = start;
            this.end = end;
        }

        public void run()
        {
            bestMove = calculateAllMoveBestResponse(board, GameBoard.depth, start, end)
                    .stream()
                    .max(Comparator.comparing(Move::getScore))
                    .orElseThrow(NoSuchElementException::new);
            allDone.release();
        }

        public Board getOptimizedBoard()
        {
            return board;
        }

        public void setOptimizedBoard(Board board)
        {
            this.board = board;
        }

        public Move getBestMove()
        {
            return bestMove;
        }

        public void setBestMove(Move bestMove)
        {
            this.bestMove = bestMove;
        }

        public List<Move> calculateAllMoveBestResponse(Board board, int depth, int start, int end)
        {
            System.out.println("Computing moves between " + start + " and " + (end-1));
            boolean isWhiteToMove = board.isWhiteToMove();
            board.computePossibleMoves();
            List<Move> moveList   = new ArrayList<>(board.getPossibleMoves());
            List<Move> resultList = new ArrayList<>();

            for (int i = start; i < end; i++) {
                Move move = moveList.get(i);
                makeMove(board, move);
                Move bestResponse = calculate2(board, depth);
                move.setBestResponse(bestResponse);
                System.out.println("Precomputed moves for " + move + " score is " + move.moveScore());
                undoMove(board, move, isWhiteToMove);
                updateMoveWithResponse(move, bestResponse);

                resultList.add(move);

//                if (!GameBoard.waitingForOpponentMove()) {
//                    // log.info("Precomputing stopped by actual move");
//                    return resultList;
//                }
            }
            return resultList;
        }

        public Move calculate2(Board board, int depth)
        {
            final int currentDepth;
            if (board.lastMove() != null && board.lastMove().getTakenPiece() != null) {
                currentDepth = depth;
            }
            else {
                currentDepth = depth;
            }
            board.computePossibleMoves();
            List<Move> moveList      = GameOptions.extractMoves(board.getPossibleMoves(), currentDepth);
            boolean    isWhiteToMove = board.isWhiteToMove();

            //stalemate or checkmate
            if (moveList.size() == 0) {
                if (board.lastMove().isCheckMate()) {
                    return GameOptions.checkMate();
                }
                return GameOptions.staleMate();
            }
            //forcedMove
            if (isForcedMove(moveList)) {
                return moveList.get(0);
            }
            if (currentDepth == 1) {
                return moveList.stream().peek(move -> MoveUpdateHelper.moveUpdate(board, move))
                        .max(Comparator.comparing(Move::getScore))
                        .orElseThrow(NoSuchElementException::new);
            }

            return moveList.stream().peek(move -> {
                board.setTurn(isWhiteToMove);
                makeMove(board, move);
                int depth_increase = 0;
                if (move.getTakenPiece() != null) {
                    depth_increase = 0;
                }
                Move bestResponse = calculate2(board, currentDepth - 1 + depth_increase);
                undoMove(board, move, isWhiteToMove);
                updateMoveWithResponse(move, bestResponse);
                move.setBestResponse(bestResponse);
                if (GameBoard.depth == currentDepth) {
                    // log.info("Computed score for move:" + move + " " + "score is " + move.moveScore() + "(" + index + "/" + length + ")");
                }
            }).max(Comparator.comparing(Move::moveScore))
                    .orElseThrow(NoSuchElementException::new);
        }

        private void updateMoveWithResponse(Move move, Move response)
        {
            move.setScore(move.getScore() - response.getScore());
        }

        private void makeMove(Board board, Move move)
        {
            MoveUpdateHelper.moveUpdate(board, move);
            board.move(move);
            board.nextTurn();
        }

        private void undoMove(Board board, Move move, boolean isWhiteToMove)
        {
            board.setTurn(isWhiteToMove);
            board.undoMove(move);
        }

        private boolean isForcedMove(List<Move> moveList)
        {
            return moveList.size() == 1;
        }

    }
}

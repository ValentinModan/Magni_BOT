package toDelete;

import board.Board;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.GameBoard;
import game.gameSetupOptions.GameOptions;

import java.util.*;
import java.util.concurrent.Semaphore;

public class AllPossibleMovesMultiThreaded
{
    private static final int          FINAL_THREAD_COUNT = 8;
    private static       int          THREAD_COUNT       = 8;
    private static       List<Worker> workerList;
    boolean firstTime = true;
    private Semaphore allDone;


    public Move possibleMoves(Board board, int depth) throws CloneNotSupportedException
    {
        board.computePossibleMoves();
        if (true) {
            THREAD_COUNT = Math.min(board.getPossibleMoves().size(), FINAL_THREAD_COUNT);
            setup(board, depth);
            allDone = new Semaphore(0);
            for (int i = 0; i < THREAD_COUNT; i++) {
                workerList.get(i).setOptimizedBoard((Board) board.clone());
                workerList.get(i).setDepth(depth);
                workerList.get(i).start();
            }
        }
        else {
            for (int i = 0; i < THREAD_COUNT; i++) {
                workerList.get(i).setOptimizedBoard((Board) board.clone());
                workerList.get(i).setDepth(depth);
                workerList.get(i).run();
            }
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
        return workerList.stream().map(worker -> worker.bestMove).filter(Objects::nonNull).max(Comparator.comparing(Move::moveScore))
                .orElseThrow(NoSuchElementException::new);

    }

    private void setup(Board board, int depth) throws CloneNotSupportedException
    {
        workerList = new ArrayList<>();
        int workersNumber       = THREAD_COUNT;
        int possibleMovesLength = board.getPossibleMoves().size();

        int lenOneWorker = possibleMovesLength / workersNumber;
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * lenOneWorker;
            int end   = (i + 1) * lenOneWorker;

            if (i == workersNumber - 1) {
                end = possibleMovesLength;
            }
            Worker worker = new Worker((Board) board.clone(), i, start, end, depth);
            workerList.add(worker);
            // worker.start();
            firstTime = false;
        }
    }

    private class Worker extends Thread
    {
        final int threadNumber;

        final int start;
        final int end;
        int   depth;
        Board board;

        Move bestMove;
        int  possibleMoves = 0;

        public int getDepth()
        {
            return depth;
        }

        public void setDepth(int depth)
        {
            this.depth = depth;
        }

        public Worker(Board board, int threadNumber, int start, int end, int depth)
        {
            this.threadNumber = threadNumber;
            this.board = board;
            this.start = start;
            this.end = end;
            this.depth = depth;
        }

        public int getPossibleMoves()
        {
            return possibleMoves;
        }

        public void setPossibleMoves(int possibleMoves)
        {
            this.possibleMoves = possibleMoves;
        }

        @Override
        public void run()
        {
            super.run();
            System.out.println("Thread_number_" + threadNumber + ": has started");
            try {
                bestMove = calculateAllMoveBestResponse(board, depth, start, end)
                        .stream()
                        .max(Comparator.comparing(Move::getScore))
                        .orElseThrow(NoSuchElementException::new);
            } catch (Exception e) {
                System.out.println("Thread " + threadNumber + " raised an exception");
                e.printStackTrace();
            }
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
            System.out.println("Computing moves between " + start + " and " + (end - 1));
            boolean isWhiteToMove = board.isWhiteToMove();
            board.computePossibleMoves();
            List<Move> moveList   = new ArrayList<>(board.getPossibleMoves());
            List<Move> resultList = new ArrayList<>();


            for (int i = start; i < end; i++) {
                Move move = moveList.get(i);
                System.out.println("Thread " + threadNumber + " has move " + move);
                makeMove(board, move);
                Move bestResponse = CleanMoveCalculator.calculate2(board, depth - 1);

                move.setBestResponse(bestResponse);
                undoMove(board, move, isWhiteToMove);

                resultList.add(move);
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
            List<Move> moveList      = board.getPossibleMoves();
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
                return moveList.stream().peek(move -> {
                                                  MoveUpdateHelper.moveUpdate(board, move);
                                                  possibleMoves++;
                                              }
                )
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

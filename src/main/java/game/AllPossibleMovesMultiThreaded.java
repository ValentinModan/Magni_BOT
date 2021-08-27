package game;

import board.OptimizedBoard;
import board.moves.Move;
import board.moves.MoveUpdateHelper;
import game.gameSetupOptions.GameOptions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class AllPossibleMovesMultiThreaded
{
    private static final int    FINAL_THREAD_COUNT = 8;
    private static  int          THREAD_COUNT = 8;
    private static List<Worker> workerList;
    boolean firstTime = true;
    private Semaphore allDone;


    public Move possibleMoves(OptimizedBoard board, int depth) throws CloneNotSupportedException
    {
        board.computePossibleMoves();
        if (true) {
            THREAD_COUNT = Math.min(board.getPossibleMoves().size(),FINAL_THREAD_COUNT);
            setup(board, depth);
            allDone = new Semaphore(0);
            for (int i = 0; i < THREAD_COUNT; i++) {
                workerList.get(i).setOptimizedBoard((OptimizedBoard) board.clone());
                workerList.get(i).setDepth(depth);
                workerList.get(i).start();
            }
        }
        else
        {
            for (int i = 0; i < THREAD_COUNT; i++) {
                workerList.get(i).setOptimizedBoard((OptimizedBoard) board.clone());
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
        int sum = 0;
        return workerList.stream().map(worker -> worker.bestMove).max(Comparator.comparing(Move::moveScore))
                .orElseThrow(NoSuchElementException::new);

    }

    private void setup(OptimizedBoard board, int depth) throws CloneNotSupportedException
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
            Worker worker = new Worker((OptimizedBoard) board.clone(), i, start, end, depth);
            workerList.add(worker);
            // worker.start();
            firstTime = false;
        }
    }

    private class Worker extends Thread
    {
        int threadNumber;

        int start, end;
        int            depth;
        OptimizedBoard optimizedBoard;

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

        public Worker(OptimizedBoard optimizedBoard, int threadNumber, int start, int end, int depth)
        {
            this.threadNumber = threadNumber;
            this.optimizedBoard = optimizedBoard;
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
            bestMove = calculateAllMoveBestResponse(optimizedBoard, depth, start, end)
                    .stream()
                    .max(Comparator.comparing(Move::getScore))
                    .orElseThrow(NoSuchElementException::new);
            allDone.release();
        }

        public OptimizedBoard getOptimizedBoard()
        {
            return optimizedBoard;
        }

        public void setOptimizedBoard(OptimizedBoard optimizedBoard)
        {
            this.optimizedBoard = optimizedBoard;
        }

        public Move getBestMove()
        {
            return bestMove;
        }

        public void setBestMove(Move bestMove)
        {
            this.bestMove = bestMove;
        }

        public List<Move> calculateAllMoveBestResponse(OptimizedBoard optimizedBoard, int depth, int start, int end)
        {
            System.out.println("Computing moves between " + start + " and " + (end - 1));
            boolean isWhiteToMove = optimizedBoard.isWhiteToMove();
            optimizedBoard.computePossibleMoves();
            List<Move> moveList   = new ArrayList<>(optimizedBoard.getPossibleMoves());
            List<Move> resultList = new ArrayList<>();


            for (int i = start; i < end; i++) {
                Move move = moveList.get(i);
                System.out.println("Thread " + threadNumber + " has move " + move);
                makeMove(optimizedBoard, move);
                Move bestResponse = CleanMoveCalculator.calculate2(optimizedBoard, depth - 1);

                move.setBestResponse(bestResponse);
                undoMove(optimizedBoard, move, isWhiteToMove);

                resultList.add(move);
            }
            return resultList;
        }

        public Move calculate2(OptimizedBoard optimizedBoard, int depth)
        {

            final int currentDepth;
            if (optimizedBoard.lastMove() != null && optimizedBoard.lastMove().getTakenPiece() != null) {
                currentDepth = depth;
            }
            else {
                currentDepth = depth;
            }
            optimizedBoard.computePossibleMoves();
            List<Move> moveList      = optimizedBoard.getPossibleMoves();
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
                return moveList.stream().peek(move -> {
                                                  MoveUpdateHelper.moveUpdate(optimizedBoard, move);
                                                  possibleMoves++;
                                              }
                )
                        .max(Comparator.comparing(Move::getScore))
                        .orElseThrow(NoSuchElementException::new);
            }

            return moveList.stream().peek(move -> {
                optimizedBoard.setTurn(isWhiteToMove);
                makeMove(optimizedBoard, move);
                int depth_increase = 0;
                if (move.getTakenPiece() != null) {
                    depth_increase = 0;
                }
                Move bestResponse = calculate2(optimizedBoard, currentDepth - 1 + depth_increase);
                undoMove(optimizedBoard, move, isWhiteToMove);
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

        private void makeMove(OptimizedBoard optimizedBoard, Move move)
        {
            MoveUpdateHelper.moveUpdate(optimizedBoard, move);
            optimizedBoard.move(move);
            optimizedBoard.nextTurn();
        }

        private void undoMove(OptimizedBoard optimizedBoard, Move move, boolean isWhiteToMove)
        {
            optimizedBoard.setTurn(isWhiteToMove);
            optimizedBoard.undoMove(move);
        }

        private boolean isForcedMove(List<Move> moveList)
        {
            return moveList.size() == 1;
        }
    }


}

package game.multithreadedmap;

import board.OptimizedBoard;
import board.moves.Move;
import game.GameBoard;
import mapmovement.MovementMap;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MultiThreadedCalculator
{
    private static final int          FINAL_THREAD_COUNT = 8;
    private static       int          THREAD_COUNT       = 8;
    private static       int          SLEEP_TIME         = 5000;
    private static       List<Worker> workerList         = new ArrayList<>();

    boolean setupHasBeenMade = false;

    public Move possibleMoves(OptimizedBoard board) throws CloneNotSupportedException, InterruptedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);
        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }

        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //update current move from the game
        Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        MovementMap.makeMovement(bestResponse);
        return bestResponse;
    }

    private void setup(OptimizedBoard board, int depth) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, board.lastMove());
        System.out.println("Adding movement to queue" + movementMap);
        generateWorkers(depth);
        startWorkers();
        System.out.println("Adding element to queue");
        MovementMap.movementMapQueue.put(movementMap);
        System.out.println("Element added");
    }

    private void generateWorkers(int depth)
    {
        for (int i = 1; i <= THREAD_COUNT; i++) {
            workerList.add(new Worker(depth));
        }
    }

    private void startWorkers()
    {
        for (Worker worker : workerList) {
            worker.start();
        }
    }

    private class Worker extends Thread
    {
        int depth;

        public Worker(int depth)
        {
            this.depth = depth;
        }

        @Override
        public void run()
        {
            super.run();
            while (true) {

                System.out.println("Worker taking move from queue with size:" + MovementMap.movementMapQueue.size());
                MovementMap movementMap = null;
                try {
                    movementMap = MovementMap.movementMapQueue.take();
                    System.out.println("Worker taken movementMap: " + movementMap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {

                    //  if (movementMap.getCurrentDepth() > depth) {
                    //move to the back of the queue

                    //     MovementMap.movementMapQueue.add(movementMap);
                    //  }
                    if (movementMap.isCurrentMovePossible() || movementMap.getParent() == null) {
                        //the move must be possible for the current game
                        System.out.println("Processing move " + movementMap.getCurrentMove());

                        OptimizedBoard board = movementMap.getBoardForCurrentPosition();
                        board.computePossibleMoves();

                        List<Move> possibleMovesCalculatorsList = board.getPossibleMoves();
                        for (Move move : possibleMovesCalculatorsList) {
                            movementMap.addResponse(move);

                        }
                        System.out.println("Processing done");
                    }
                    else {
                        System.out.println("Move not taken becuase is not possible");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("ERROR: trying to get empty element from movementMapQueue");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

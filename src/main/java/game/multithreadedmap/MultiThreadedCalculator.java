package game.multithreadedmap;

import board.Board;
import board.moves.Move;
import game.GameBoard;
import lombok.SneakyThrows;
import mapmovement.MovementMap;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;

public class MultiThreadedCalculator
{
    private static final int          THREAD_COUNT = 1;
    private static final List<Worker> workerList   = new ArrayList<>();

    boolean setupHasBeenMade = false;

    public Move possibleMoves(Board board) throws CloneNotSupportedException, InterruptedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);
        }
        else {
            //making enemy move
            MovementMap.makeMovement(board.lastMove());
        }

        sleep(8000);
        //update current move from the game
        Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        MovementMap.makeMovement(bestResponse);
        return bestResponse;
    }

    private void setup(Board board, int depth) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, board.lastMove());
        MovementMap.currentMoveFromTheGame = movementMap;

        generateWorkers(depth);

        startWorkers();

        MovementMap.movementMapQueue.add(movementMap);
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

        @SneakyThrows
        @Override
        public void run()
        {
            super.run();
            while (true) {
                MovementMap movementMap;

                movementMap = MovementMap.movementMapQueue.remove();
//                    if (movementMap.getCurrentDepth() > depth) {
//                        //move to the back of the queue
//                        MovementMap.movementMapQueue.add(movementMap);
//                    }
                if (movementMap.isCurrentMovePossible() || movementMap.getParent() == null) {
                    //the move must be possible for the current game
                    Board board = movementMap.getBoardForCurrentPosition();
                    board.computePossibleMoves();

                    List<Move> possibleMovesCalculatorsList = board.getPossibleMoves();
                    for (Move move : possibleMovesCalculatorsList) {
                        movementMap.addResponse(move);
                    }
                }
            }
        }


    }
}

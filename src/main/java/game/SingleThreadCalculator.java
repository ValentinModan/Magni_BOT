package game;

import board.Board;
import board.moves.Move;
import board.pieces.PieceType;
import game.gameSetupOptions.GameOptions;
import lombok.extern.slf4j.Slf4j;
import mapmovement.MovementMap;

import java.util.List;

@Slf4j
public class SingleThreadCalculator
{
    boolean setupHasBeenMade = false;

    public static int movesLowerThanDepth = 0;
    public static int depthMoves = 120000;

    public Move bestResponse(Board board) throws InterruptedException, CloneNotSupportedException
    {
        if (!setupHasBeenMade) {
            setupHasBeenMade = true;
            setup(board, GameBoard.depth);
        }
        else {
            MovementMap.makeMovement(board.lastMove());
        }
        movesLowerThanDepth = depthMoves;
        computeAllDepth();

        //Move bestResponse = MovementMap.currentMoveFromTheGame.getCurrentMove().getBestResponse();
        Move bestResponse = getBestResponse(MovementMap.currentMoveFromTheGame);
        log.info("Best move chosen is " + bestResponse);
        Move display = bestResponse;

        MovementMap displayMap = MovementMap.currentMoveFromTheGame;
        while (display != null) {

            log.info("Best response for this one is ", display);
            displayMap  = displayMap.getMovementMap().get(displayMap);
            display = getBestResponse(displayMap);
        }

        MovementMap.makeMovement(bestResponse);

        if (board.allMoves.size() % 5 == 0) {
            System.gc();
            System.out.println("GC done");
        }
        clearImpossibleMovesFromQueue();
        return bestResponse;
    }

    private void clearImpossibleMovesFromQueue()
    {
        int n = MovementMap.movementMapQueue.size();

        for (int i = 1; i < n; i++) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();
            if ((movementMap.isCurrentMovePossible() || movementMap.getParent() == null)) {
                MovementMap.movementMapQueue.add(movementMap);
            }
        }
    }

    private Move getBestResponse(MovementMap movementMap)
    {
        if(movementMap==null || movementMap.getCurrentMove()==null)
        {
            return null;
        }
        Move bestMove = movementMap.getCurrentMove().getBestResponse();

        for(Move move:movementMap.getMovementMap().keySet())
        {
            if(bestMove.moveScore()< movementMap.getMovementMap().get(move).getCurrentMove().moveScore())
            {
                bestMove = move;
            }
        }
        return bestMove;
    }


    public void computeAllDepth() throws CloneNotSupportedException
    {
        while (movesLowerThanDepth > 0 && MovementMap.movementMapQueue.size() > 0) {
            MovementMap movementMap = MovementMap.movementMapQueue.remove();

            if (movementMap.isCurrentMovePossible() || movementMap.getParent() == null) {
                if (movementMap.getCurrentDepth() > GameBoard.depth) {
                    MovementMap.movementMapQueue.add(movementMap);
                }
                else {
                    Board board = movementMap.getBoardForCurrentPosition();
                    board.computePossibleMoves();
                    List<Move> possibleMovesCalculatorsList = board.getPossibleMoves();
                    if(possibleMovesCalculatorsList.isEmpty())
                    {
                        movementMap.getCurrentMove().setCheckMate(true);
                        movementMap.getParent().updateScore(movementMap.getCurrentMove());
                    }
                    for (Move move : possibleMovesCalculatorsList) {
                        if (movementMap.getCurrentMove().getTakenPiece() == null || (movementMap.getCurrentMove().getTakenPiece() != null &&
                                movementMap.getCurrentMove().getTakenPiece().getPieceType() != PieceType.KING)) {
                            movementMap.addResponse(move);
                        }

                    }
                    movesLowerThanDepth--;
                    if (movesLowerThanDepth % 2000 == 0) {
                       // log.debug("Remaning moves left for current move: " + movesLowerThanDepth);
                    }
                }
            }

        }
    }

    public void setup(Board board, int depth) throws InterruptedException
    {
        board.computePossibleMoves();
        MovementMap movementMap = new MovementMap(null, board.lastMove());
        MovementMap.currentMoveFromTheGame = movementMap;

        MovementMap.movementMapQueue.add(movementMap);
        movesLowerThanDepth++;

    }
}

package mapmovement;

import board.moves.Move;
import board.moves.Movement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MovementMap
{
    // map of all possible replies
    private final Map<Move, MovementMap> movementMap = new HashMap<>();

    //the move before
    private MovementMap            parent;

    //the current move
    private Move                   currentMove;

    //score including the best response
    private AtomicInteger          score;

    protected synchronized void updateScore(Move move)
    {
        //check if it should be maximum or minimum

        //todo: check if this should be atomic or not
        AtomicBoolean isNewBestMove = new AtomicBoolean(false);

        if (score.get() < currentMove.getScore() - move.moveScore()) {

            currentMove.setBestResponse(move);
            isNewBestMove = new AtomicBoolean(true);

        }
        //update all parents until root
        if (parent != null) {
            parent.updateScore(currentMove);
        }
    }

}

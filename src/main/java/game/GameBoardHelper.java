package game;

import api.RequestController;
import api.games.GetMyOwnGoingGames;
import api.games.owngame.NowPlaying;

import java.time.LocalTime;

//TODO: move methods to helper
public class GameBoardHelper
{

    private static      LocalTime previousRequestTime     = LocalTime.now();
    private static final int       MY_TURN_REQUEST_TIMEOUT = 10;

    public static boolean hasRequestTimeoutPassed()
    {
        boolean hasRequestTimeoutPassed = LocalTime.now().isAfter(previousRequestTime.plusSeconds(MY_TURN_REQUEST_TIMEOUT));
        if (hasRequestTimeoutPassed) {
            previousRequestTime = LocalTime.now();
        }
        return hasRequestTimeoutPassed;
    }

    public static boolean isFirstMoveOfTheGame(NowPlaying nowPlaying)
    {
        return nowPlaying.getLastMove().equals("");
    }


    public static void computeNewDepth()
    {
        int depth = GameBoard.depth;
        //        int opponentPiecesLeft = actualBoard.opponentPiecesLeft();
//        if (opponentPiecesLeft >= 10) {
//            depth = GameBoard.DEFAULT_DEPTH;
//        }
//        else {
//            depth = GameBoard.DEFAULT_DEPTH + (16 - opponentPiecesLeft) / 4;
//            if (depth > MAX_DEPTH) {
//                depth = MAX_DEPTH;
//            }
//            if (actualBoard.myPiecesLeft() + opponentPiecesLeft >= 8) {
//                depth = MAX_DEPTH_MID_GAME;
//            }
//        }
//      depth += OptimizedBoard.actualMoves.size() / 60;
        //  System.out.println("Computing for new depth: " + depth);

        GameBoard.depth = depth;
    }
}

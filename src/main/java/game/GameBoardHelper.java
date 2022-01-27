package game;

import api.RequestController;
import api.games.GetMyOwnGoingGames;
import api.games.NowPlaying;
import board.Board;
import board.MovementMap;

import java.time.LocalTime;

import static game.GameBoard.getMyOwnGoingGames;

//TODO: move methods to helper
public class GameBoardHelper
{
    private static LocalTime previousRequestTime = LocalTime.now();
    private static final int MY_TURN_REQUEST_TIMEOUT = 10;

    public static boolean hasRequestTimeoutPassed()
    {
        boolean hasRequestTimeoutPassed = LocalTime.now().isAfter(previousRequestTime.plusSeconds(MY_TURN_REQUEST_TIMEOUT));
        if (hasRequestTimeoutPassed) {
            previousRequestTime = LocalTime.now();
        }
        return hasRequestTimeoutPassed;
    }

    public static boolean isMyTurn()
    {
        if (GameBoardHelper.hasRequestTimeoutPassed()) {
            getMyOwnGoingGames = (GetMyOwnGoingGames) RequestController.sendRequest(getMyOwnGoingGames);
            return getMyOwnGoingGames.getNowPlaying().get(0).getIsMyTurn().equals("true");
        }
        return false;
    }

    public static boolean isFirstMoveOfTheGame(NowPlaying nowPlaying)
    {
        return nowPlaying.getLastMove().equals("");
    }
}

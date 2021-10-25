package game;

import api.games.owngame.NowPlaying;

//TODO: move methods to helper
public class GameBoardHelper
{
    public static boolean isFirstMoveOfTheGame(NowPlaying nowPlaying)
    {
        return nowPlaying.getLastMove().equals("");
    }
}

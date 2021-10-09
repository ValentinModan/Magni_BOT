package game;

import api.games.owngame.NowPlaying;

public class GameBoardHelper
{
    public static boolean isFirstMoveOfTheGame(NowPlaying nowPlaying)
    {
        return nowPlaying.getLastMove().equals("");
    }
}

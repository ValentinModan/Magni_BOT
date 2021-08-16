package api.games;

import api.RequestAPI;
import api.games.owngame.NowPlaying;
import org.springframework.http.HttpMethod;

import java.util.List;

public class GetMyOwnGoingGames implements RequestAPI
{
    public static final String API = "https://lichess.org/api/account/playing";

    private List<NowPlaying> nowPlaying;


    public List<NowPlaying> getNowPlaying()
    {
        return nowPlaying;
    }

    public void setNowPlaying(List<NowPlaying> nowPlaying)
    {
        this.nowPlaying = nowPlaying;
    }

    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.GET;
    }

    @Override
    public String getAPI()
    {
        return API;
    }
}

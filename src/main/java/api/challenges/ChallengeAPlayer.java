package api.challenges;

import api.RequestAPI;
import org.springframework.http.HttpMethod;

public class ChallengeAPlayer implements RequestAPI
{
    private String playerName;

    public ChallengeAPlayer(String playerName)
    {
        this.playerName = playerName;
    }

    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.POST;
    }

    @Override
    public String getAPI()
    {
        return  "https://lichess.org/api/challenge/" + playerName;
    }
}

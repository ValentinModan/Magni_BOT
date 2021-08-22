package api.bot;

import api.RequestAPI;
import org.springframework.http.HttpMethod;

public class MakeABotMove implements RequestAPI
{

    private String move;
    private String gameId;

    private static final String GAME_ID = "{gameId}";
    private static final String MOVE    = "{move}";

    private static final String API = "https://lichess.org/api/game/{gameId}/move/{move}";

    public MakeABotMove(String gameId,String move)
    {
        this.move = move;
        this.gameId = gameId;
    }

    public MakeABotMove()
    {
    }

    public String getMove()
    {
        return move;
    }

    public void setMove(String move)
    {
        this.move = move;
    }

    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.POST;
    }

    @Override
    public String getAPI()
    {
        return "https://lichess.org/api/board/game/" + gameId + "/move/" + move;
    }
}

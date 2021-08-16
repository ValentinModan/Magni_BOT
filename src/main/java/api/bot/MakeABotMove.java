package api.bot;

import api.RequestAPI;
import org.springframework.http.HttpMethod;

public class MakeABotMove implements RequestAPI
{

    private String move;
    private String gameId;

    private static final String GAME_ID = "{gameId}";
    private static final String MOVE    = "{move}";

    private static final String API = "https://lichess.org/api/bot/game/{gameId}/move/{move}";

    public MakeABotMove(String gameId,String move)
    {
        this.move = move;
        this.gameId = gameId;
    }

    public MakeABotMove()
    {
    }

    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.POST;
    }

    @Override
    public String getAPI()
    {
        return API.replace(GAME_ID, gameId).replace(MOVE, move);
    }
}

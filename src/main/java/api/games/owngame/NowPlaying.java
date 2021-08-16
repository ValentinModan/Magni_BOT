package api.games.owngame;

public class NowPlaying
{

    private String gameId;

    private String color;

    private String isMyTurn;


    public String getGameId()
    {
        return gameId;
    }

    public void setGameId(String gameId)
    {
        this.gameId = gameId;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getIsMyTurn()
    {
        return isMyTurn;
    }

    public void setIsMyTurn(String isMyTurn)
    {
        this.isMyTurn = isMyTurn;
    }
}

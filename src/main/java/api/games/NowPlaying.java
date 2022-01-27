package api.games;

public class NowPlaying
{

    private String gameId;

    private String color;

    private String isMyTurn;

    private String lastMove;


    public String getLastMove()
    {
        return lastMove;
    }

    public void setLastMove(String lastMove)
    {
        this.lastMove = lastMove;
    }

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

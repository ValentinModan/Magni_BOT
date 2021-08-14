package api.json;

public class Input
{
    private String id;
    private String url;
    private Challenger challenger;

    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Challenger getChallenger()
    {
        return challenger;
    }

    public void setChallenger(Challenger challenger)
    {
        this.challenger = challenger;
    }

    @Override
    public String toString()
    {
        return "Input{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", challenger=" + challenger +
                ", status='" + status + '\'' +
                '}';
    }
}

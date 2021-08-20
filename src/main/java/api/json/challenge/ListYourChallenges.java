package api.json.challenge;


import api.RequestAPI;
import api.json.challenge.property.Input;
import api.json.challenge.property.Output;
import org.springframework.http.HttpMethod;

import java.util.List;

public class ListYourChallenges implements RequestAPI
{
    public static final String API = "https://lichess.org/api/challenge";

    private List<Input> in;

    private List<Output> out;

    public List<Input> getIn()
    {
        return in;
    }

    public void setIn(List<Input> in)
    {
        this.in = in;
    }

    public List<Output> getOut()
    {
        return out;
    }

    public void setOut(List<Output> out)
    {
        this.out = out;
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

    @Override
    public String toString()
    {
        return "ListYourChallenges{" +
                "in=" + in +
                ", out=" + out +
                '}';
    }
}

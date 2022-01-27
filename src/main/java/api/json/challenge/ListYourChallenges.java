package api.json.challenge;


import api.RequestAPI;
import api.json.challenge.property.Input;
import org.springframework.http.HttpMethod;

import java.util.List;

//todo: rename this class
public class ListYourChallenges implements RequestAPI
{
    public static final String API = "https://lichess.org/api/challenge";

    private List<Input> in;


    public List<Input> getIn()
    {
        return in;
    }

    public void setIn(List<Input> in)
    {
        this.in = in;
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
                ",+"+
                '}';
    }
}

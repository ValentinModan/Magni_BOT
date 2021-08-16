package api.account;

import api.RequestAPI;
import org.springframework.http.HttpMethod;

public class AccountUpgrade implements RequestAPI
{
    private static final String API = "https://lichess.org/api/bot/account/upgrade";
    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.POST;
    }

    @Override
    public String getAPI()
    {
        return API;
    }
}

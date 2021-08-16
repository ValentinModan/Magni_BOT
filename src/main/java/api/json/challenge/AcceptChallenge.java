package api.json.challenge;

import api.RequestAPI;
import org.springframework.http.HttpMethod;

public class AcceptChallenge implements RequestAPI
{

    private static final String CHALLENGE_ID= "{challengeId}";
    private static final String API = "https://lichess.org/api/challenge/{challengeId}/accept";

    private String challengeID;

    private boolean ok;

    public AcceptChallenge()
    {
    }

    public AcceptChallenge(String challengeID)
    {
        this.challengeID = challengeID;
    }

    @Override
    public HttpMethod getRequestType()
    {
        return HttpMethod.POST;
    }

    @Override
    public String getAPI()
    {
        return API.replace(CHALLENGE_ID,challengeID);
    }
}

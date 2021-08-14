package api.challenges;

import api.json.ChallengeJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Challenge
{

    public static void getChallenges()
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders  httpHeaders  = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer TgrJxTCQpKTt7gqm");
        //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body",httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("https://lichess.org/api/challenge", HttpMethod.GET,httpEntity,String.class);


        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        System.out.println("Response is:");
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("in"));
        System.out.println(jsonObject.getAsJsonArray("in"));
        System.out.println(jsonObject.getAsJsonArray("in").get(0).getAsJsonObject());
        System.out.println(jsonObject.getAsJsonArray("in").get(0).getAsJsonObject().get("challenger"));
        System.out.println(jsonObject.getAsJsonArray("in").get(0).getAsJsonObject().get("challenger").getAsJsonObject().get("id"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ChallengeJson challengeJson = objectMapper.readValue(response.getBody(),ChallengeJson.class);
            System.err.println("##############################");
            System.err.println(challengeJson.getIn().toString());


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}

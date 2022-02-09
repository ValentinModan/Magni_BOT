package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestController
{

    private static final String bearedToken = "TgrJxTCQpKTt7gqm";

    public static RequestAPI sendRequest(RequestAPI requestAPI)
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer " + bearedToken);
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestAPI.getAPI(), requestAPI.getRequestType(), httpEntity, String.class);


        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestAPI requestAPI1 = null;
        try {
            requestAPI1 = objectMapper.readValue(response.getBody(), requestAPI.getClass());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestAPI1;
    }

    public static RequestAPI sendRequestWithProperties(RequestAPI requestAPI, int timeInSeconds, int increment, boolean rated)
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer " + bearedToken);
        //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");

        System.out.println(httpHeaders.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("body", "A powerful tool for building web apps.");
        map.put("color", "random");
        map.put("rated", rated);

        Map<String, Object> timeMap = new HashMap<>();
        timeMap.put("limit", timeInSeconds);
        timeMap.put("increment", increment);
        map.put("clock", timeMap);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestAPI.getAPI(), requestAPI.getRequestType(), entity, String.class);


        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestAPI requestAPI1 = null;
        try {
            requestAPI1 = objectMapper.readValue(response.getBody(), requestAPI.getClass());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return requestAPI1;
    }
}

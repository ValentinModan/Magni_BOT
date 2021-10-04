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

@Slf4j
public class RequestController
{

    public static RequestAPI sendRequest(RequestAPI requestAPI)
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders  httpHeaders  = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer TgrJxTCQpKTt7gqm");
        //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body",httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestAPI.getAPI(), requestAPI.getRequestType(),httpEntity,String.class);


        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestAPI requestAPI1 = null;
        try {
            requestAPI1 = objectMapper.readValue(response.getBody(), requestAPI.getClass());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestAPI1;
    }
}

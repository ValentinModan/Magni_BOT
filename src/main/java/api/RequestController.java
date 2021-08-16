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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders  httpHeaders  = new HttpHeaders();
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer TgrJxTCQpKTt7gqm");
        //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body",httpHeaders);


        System.err.println(requestAPI.getAPI());
        ResponseEntity<String> response = restTemplate.exchange(requestAPI.getAPI(), requestAPI.getRequestType(),httpEntity,String.class);


        JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();

        log.info(jsonObject.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestAPI requestAPI1 = null;
        try {
            requestAPI1 = objectMapper.readValue(response.getBody(), requestAPI.getClass());
            System.err.println("##############################");
            System.err.println(requestAPI1.toString());



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return requestAPI1;


    }
}

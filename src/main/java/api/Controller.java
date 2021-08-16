package api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Controller {

    @Value("{game_id}")
    private String asd;

    public void sentRequest() {
        URL url = null;
        try {
            url = new URL("https://lichess.org");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Bearer TgrJxTCQpKTt7gqm");

            System.out.println("Request response is:");
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRestTemplateRequest(String gameId, String move) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer TgrJxTCQpKTt7gqm");
       //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body",httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("https://lichess.org/api/board/game/" + gameId + "/move/" + move, HttpMethod.POST,httpEntity,String.class);


        System.out.println("Response is:");
        System.out.println(response);
    }

    public void sendMove() {
        try {
            URL url = new URL("https://lichess.org/api/board/game/S68FFz8F/move/a7a5");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Bearer TgrJxTCQpKTt7gqm");
            http.setRequestProperty("Content-Type", "");
            http.setRequestProperty("Content-Length", "0");

            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

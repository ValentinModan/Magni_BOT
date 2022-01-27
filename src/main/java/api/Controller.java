package api;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class Controller
{

    public void sentRequest()
    {
        URL url = null;
        HttpURLConnection http = null;
        try {
            url = new URL("https://lichess.org");
            http = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Bearer TgrJxTCQpKTt7gqm");

            http.disconnect();

        } catch (Exception e) {
            System.out.println("Request response is:");
            try {
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void sendRestTemplateRequest(String gameId, String move)
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", "Bearer TgrJxTCQpKTt7gqm");
        //check if can remove
        httpHeaders.set("Content-Type", "");
        httpHeaders.set("Content-Length", "0");
        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange("https://lichess.org/api/board/game/" + gameId + "/move/" + move, HttpMethod.POST, httpEntity, String.class);

        log.info("Sending the move " + move);
    }

    public void sendMove()
    {
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
            e.printStackTrace();
        }
    }

}

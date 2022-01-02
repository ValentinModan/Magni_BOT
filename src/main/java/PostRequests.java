import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostRequests {

    public static URL url;

    private static void setUp() {
        try {
            URL url = new URL("https://lichess.org/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest()
    {
        try {
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            //set request time to POSt
            con.setRequestMethod("POST");

            //set properties
            con.setRequestProperty("Content-Type", "application/json; utf-8");

            con.setDoOutput(true);

           String jsonInputString = "{'name': 'Upendra', 'job': 'Programmer'}";

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }





        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package game;

import game.gameSetupOptions.GameConfigOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GameConfig
{
    private static final String fileName = "src/main/resources/config.txt";

    public static final Map<String, String> configMap = new HashMap<>();

    public static void config()
    {
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(it ->
                           {
                               String[] array = it.replaceAll("\\s", "")
                                       .split("=");
                              GameConfigOptions.updateConfig(array[0].toUpperCase(), array[1]);
                           });

        } catch (IOException e) {
            e.printStackTrace();
        }
        GameConfigOptions.displayConfig();
    }
}

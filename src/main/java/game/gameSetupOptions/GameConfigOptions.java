package game.gameSetupOptions;

import game.GameConfig;
import game.GameConfigEnum;
import lombok.extern.slf4j.Slf4j;

import static game.GameConfigEnum.*;

@Slf4j
public class GameConfigOptions
{
    public static int timeInSeconds = 600;
    public static int increment = 10;
    public static boolean rated = false;
    public static Boolean isWhite = null;
    public static Boolean isWaitingForChallenge = Boolean.FALSE;
    //set default opponent
    public static String opponent = null;

    public static void updateConfig(String key, String value)
    {
        GameConfigEnum gameConfigEnum = GameConfigEnum.valueOf(key);

        switch (gameConfigEnum) {
            case RATED:
                updateRated(value);
                break;
            case COLOR:
                updateColor(value);
                break;
            case CHALLENGE:
                updateChallenge(value);
                break;
            case OPPONENT:
                updateOpponent(value);
                break;
            case TIMEINSECONDS:
                timeInSeconds = Integer.parseInt(value);
                break;
            case INCREMENT:
                increment = Integer.parseInt(value);
                break;
            default:
                log.error("Invalid configuration for " + key + " " + value);
        }
    }

    private static void updateOpponent(String value)
    {
        opponent = value;
    }

    private static void updateChallenge(String value)
    {
        isWaitingForChallenge = Boolean.parseBoolean(value);
    }

    private static void updateColor(String value)
    {
        if (value.equalsIgnoreCase("white")) {
            isWhite = Boolean.TRUE;
        }
        if (value.equalsIgnoreCase("black")) {
            isWhite = Boolean.FALSE;
        }
    }

    private static void updateRated(String value)
    {
        rated = Boolean.parseBoolean(value);
    }

    public static void displayConfig()
    {
        StringBuilder stringBuilder = new StringBuilder();
        append(stringBuilder, RATED, rated);
        append(stringBuilder, COLOR, isWhite);
        append(stringBuilder, CHALLENGE, opponent);
        append(stringBuilder, TIMEINSECONDS, timeInSeconds);
        append(stringBuilder, INCREMENT, increment);

        System.out.println(stringBuilder);
    }

    public static void append(StringBuilder stringBuilder, GameConfigEnum gameConfigEnum, Object value)
    {
        stringBuilder.append(gameConfigEnum).append(" = ").append(value).append("\n");

    }

}

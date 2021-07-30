package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleReader {

    public static String readMove()
    {
        String move= "";

            Scanner reader = new Scanner(System.in);
            System.out.println("Enter our move:");
            move = reader.nextLine();

        return move.substring(0,4).toLowerCase(Locale.ROOT);
    }

    //todo
    private boolean isValidMove()
    {
        return true;
    }
}

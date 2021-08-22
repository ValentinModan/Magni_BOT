package openings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OpeningReader
{
    public static List<String> readOpenings()
    {
        List<String> resultList = new ArrayList<>();
        try {
            String expected_value = "Hello, world!";
            String file           = "src/main/resources/openings.txt";

            BufferedReader reader      = new BufferedReader(new FileReader(file));
            String         currentLine = reader.readLine();


            while (currentLine != null) {

                currentLine = reader.readLine();
                resultList.add(currentLine);
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultList;
    }

}

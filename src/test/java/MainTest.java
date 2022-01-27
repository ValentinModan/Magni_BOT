import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class MainTest
{

    @Test
    void main() throws FileNotFoundException, InterruptedException
    {
        String[] stringArray = new String[0];
        Main.main(stringArray);
    }
}
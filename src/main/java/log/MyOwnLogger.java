package log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyOwnLogger
{

    private static Logger logger;

    public static MyOwnLogger getLogger(String name)
    {
        logger = Logger.getLogger(name);
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("magni_logger.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info("My first log");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new MyOwnLogger();
    }
}

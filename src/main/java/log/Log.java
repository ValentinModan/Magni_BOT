package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class Log
{
    private static Logger logger = Logger.getLogger("MyLog");

    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("%h/TorrentDownloader.log");
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Log getLogger(String name)
    {

        logger = Logger.getLogger(name);
        logger.addHandler(fileHandler);

        return new Log();
    }


    public void warning(String message)
    {
       logger.warning(message);
    }
}
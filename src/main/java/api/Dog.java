package api;

import lombok.CustomLog;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Dog
{

    public void hello()
    {

        log.info("Log  test");
        log.debug("Log  test");
        log.error("Log  test");
    }
}

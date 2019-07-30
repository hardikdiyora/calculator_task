package logger;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

/**
 * Logger is injected to all test classes using Spring DI
 */
@Configuration
public class TALogger {
    private Logger _logger;

    /***
     * Return logger for given class
     *
     * @param c Class for which logger has to be returned
     * @return logger
     */
    public Logger getLogger(@SuppressWarnings("rawtypes") final Class c)
    {
        _logger = Logger.getLogger(c);
        return _logger;
    }
}

package server.utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

public class Logging {

    //Declaring the log variable of class Logger
    private Logger log ;

    //Method for writing the actual log with a switch for different events
    /*
     * className = name of class to be logged in
     * eventObject = the object where the event occurs "in"
     * eventDescription = description of what happens at the event
     * logLevel = the level of logging needed (used for the switch)
     */
    public void writeLog(String className, Object eventObject, String eventDescription, Integer logLevel) {

        //Initializes the log variable with the logClass
        log = LoggerFactory.getLogger(className);

        //Switch responsible for choosing the correct logLevel
        switch (logLevel) {
            case 2:
                log.debug(eventDescription, eventObject);
                break;
            case 1:
                log.error(eventDescription, eventObject);
                break;
            case 0:
                log.info(eventDescription, eventObject);
                break;
            default:
                log.info(eventDescription, eventObject);
                break;
        }
    }
}

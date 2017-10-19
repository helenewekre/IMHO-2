package server.utility;

import java.util.logging.Logger;

public class Logging {

    private Logger log;

    public void writeLog(String className, Object eventObject, String eventDescription, Integer logLevel) {
            log = LoggerFactory.getLogger(className);

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
}

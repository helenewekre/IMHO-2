package server.utility;
import server.controller.Config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

public class Globals implements ServletContextListener {

    public static Logging log = new Logging();

    public static Config config = new Config();

    //Function used to initialize Logger and Config classes
    //This method is automatically called by JERSEY when the server is started
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //We init config in order to read the file and set all the variables.
        //IOException is shown if the file doesn't exists
        try {
            config.initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //We initialize our Logger class and tells our Logging.txt that the system has been started
        System.out.println("Context is initialized");
        log.writeLog(this.getClass().getName(), this, "We've started the system", 1);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Logging when the system is stopped
        System.out.println("Context is destroyed");
        log.writeLog(this.getClass().getName(), this, "The system has been stopped", 2);
    }
}


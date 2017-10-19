package server.endpoints;

import com.google.gson.Gson;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Course;
import server.utility.Globals;
import server.utility.Logging;

import server.utility.Crypter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("/courses")
public class CourseEndpoint {

    DbManager dbmanager = new DbManager();
    Crypter crypter = new Crypter();
    Config config = new Config();

    @GET
    public Response loadCourses (){
        Globals.log.writeLog(this.getClass().getName(), this, "Loaded courses", 2);

            ArrayList<Course> courses = dbmanager.loadCourses();
            String newCourses = new Gson().toJson(courses);
            newCourses = crypter.encryptAndDecryptXor(newCourses);
            newCourses = new Gson().toJson(newCourses);

            return Response.status(200).type("application/json").entity(newCourses).build();



    }
}






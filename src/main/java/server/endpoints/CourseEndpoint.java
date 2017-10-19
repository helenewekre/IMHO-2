package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Course;
import server.utility.Globals;
import server.utility.Logging;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/courses")
public class CourseEndpoint {

    DbManager dbmanager = new DbManager();


    @GET
    public Response loadCourses (){
        Globals.log.writeLog(this.getClass().getName(), this, "Loaded courses", 2);

        ArrayList<Course> courses = dbmanager.loadCourses();

        return Response.status(200).entity(new Gson().toJson(courses)).build();

    }

}
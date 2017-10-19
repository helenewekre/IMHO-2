package server.endpoints;

import com.google.gson.Gson;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Course;
import server.utility.Crypter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/courses")
public class CourseEndpoint {

    DbManager dbmanager = new DbManager();
    Crypter crypter = new Crypter();
    Config config = new Config();

    @GET
    public Response loadCourses () {
        ArrayList<Course> courses = dbmanager.loadCourses();

        if (config.getENCRYPTION()) {
            String newCourses = new Gson().toJson(courses);
            newCourses = crypter.encryptAndDecryptXor(newCourses);

            return Response.status(200).entity(newCourses).build();

        } else {

        }
            return Response.status(200).entity(new Gson().toJson(courses)).build();

        }


    }
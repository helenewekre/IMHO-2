package server.endpoints;

import com.google.gson.Gson;
import server.controller.Config;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.Course;
import server.models.User;
import server.utility.CurrentUserContext;
import server.utility.Globals;
import server.utility.Crypter;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/courses")
public class CourseEndpoint {

    DbManager dbmanager = new DbManager();
    Crypter crypter = new Crypter();
    MainController mainController = new MainController();

    @GET
    public Response loadCourses(@HeaderParam("authorization") String token) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            ArrayList<Course> courses = dbmanager.loadCourses();
            String newCourses = new Gson().toJson(courses);
            newCourses = crypter.encryptAndDecryptXor(newCourses);

            if (courses != null) {
                Globals.log.writeLog(this.getClass().getName(), this, "Loaded courses", 2);
                return Response.status(200).type("application/json").entity(new Gson().toJson(newCourses)).build();
            } else {
                return Response.status(500).type("application/json").entity("No courses").build();
            }
        } else {
            return Response
                    .status(500 )
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }
}






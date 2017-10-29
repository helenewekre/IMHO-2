package server.endpoints;

import com.google.gson.Gson;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.Course;
import server.utility.CurrentUserContext;
import server.utility.Globals;
import server.utility.Crypter;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/course")
public class CourseEndpoint {
    DbManager dbManager = new DbManager();
    MainController mainController = new MainController();
    Crypter crypter = new Crypter();


    @GET
    public Response loadCourses(@HeaderParam("authorization") String token) throws SQLException {
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null) {
            ArrayList<Course> courses = dbManager.loadCourses();
            String allCourses = new Gson().toJson(courses);
            allCourses = crypter.encryptAndDecryptXor(allCourses);
            Globals.log.writeLog(this.getClass().getName(), this, "Courses loaded", 2);

            if (courses != null) {
                return Response.status(200).type("application/json").entity(new Gson().toJson(allCourses)).build();
            } else {
                return Response.status(204).type("text/plain").entity("No courses").build();
            }
        } else {
            Globals.log.writeLog(this.getClass().getName(), this, "Courses failed loading", 2);
            return Response.status(401).type("text/plain").entity("Unauthenticated").build();
        }
    }
}






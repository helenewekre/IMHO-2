package server.endpoints;
import com.google.gson.Gson;
import server.controller.AdminController;
import server.dbmanager.dbmanager3;
import server.models.Quiz;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/quiz")
public class QuizEndpoint {
    dbmanager3 dbmanager = new dbmanager3();
    AdminController adminController = new AdminController();

    @GET
    @Path("/{CourseID}")
    public Response loadQuizzes(@PathParam("CourseID") int courseId){
        ArrayList<Quiz> quizzes = dbmanager.loadQuizzes(courseId);

        return Response.status(200).entity(new Gson().toJson(quizzes)).build();

    }

    @POST
    public Response createQuiz(String quizJson) {

        Boolean quizCreated = adminController.createQuiz(quizJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizCreated\":\"true\"}")
                .build();
    }

}

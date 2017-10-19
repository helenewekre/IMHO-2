package server.endpoints;
import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.UserController;
import server.dbmanager.DbManager;
import server.models.Quiz;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/quiz")
public class QuizEndpoint {
    DbManager dbManager = new DbManager();
    AdminController adminController = new AdminController();

    @GET
    @Path("/{CourseID}")
    public Response loadQuizzes(@PathParam("CourseID") int courseId){
        ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);

        return Response.status(200).entity(new Gson().toJson(quizzes)).build();

    }
        // Method for creating a quiz
    @POST
    @Path("/{create}")
    public Response createQuiz(String quizJson) {

        Quiz quiz = new Gson().fromJson(quizJson, Quiz.class);
        boolean quizCreated = adminController.createQuiz(quiz);
        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizCreated\":\"true\"}")
                .build();
    }
        // Method for deleting a quiz and all it's sub-tables
    @DELETE
    @Path("{deleteId}")
    public Response deleteQuiz(@PathParam("deleteId")int idQuiz) {

        Boolean quizDeleted = adminController.deleteQuiz(idQuiz);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizDeleted\":\"true\"}")
                .build();
    }


}

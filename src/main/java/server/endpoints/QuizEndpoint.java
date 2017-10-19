package server.endpoints;
import com.google.gson.Gson;
import server.controller.AdminController;
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

    @POST
    @Path("/{create}")
    public Response createQuiz(String quizJson) {
        Boolean quizCreated = adminController.createQuiz(quizJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizCreated\":\"true\"}")
                .build();
    }

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

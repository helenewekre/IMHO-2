package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.controller.UserController;
import server.dbmanager.DbManager;
import server.models.Quiz;
import server.models.User;
import server.utility.CurrentUserContext;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/quiz")
public class QuizEndpoint {
    DbManager dbManager = new DbManager();
    AdminController adminController = new AdminController();
    MainController mainController = new MainController();


    @GET
    @Path("/{CourseID}")
    public Response loadQuizzes(@PathParam("CourseID") int courseId) {
        ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);

        return Response.status(200).type("application/json").entity(new Gson().toJson(quizzes)).build();

    }
        // Method for creating a quiz
    @POST
    public Response createQuiz(@HeaderParam("authorization") String token, String quizJson) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.isAdmin()) {
            Quiz quizCreated = adminController.createQuiz(quizJson);

            return Response.status(200).type("application/json")
                    .entity(new Gson().toJson(quizCreated))
                    .build();
        }
        return Response
                .status(403)
                .type("application/json")
                .entity("{\"error\":\"No permissions\"}")
                .build();


    }
        // Method for deleting a quiz and all it's sub-tables
    @DELETE
    @Path("{deleteId}")
    public Response deleteQuiz(@PathParam("deleteId")int quizJson) {

        Boolean quizDeleted = adminController.deleteQuiz(quizJson);


        if (quizDeleted = true) {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Quiz is deleted")
                    .build();
        } else {
            return Response
                    .status(500)
                    .type("application/json")
                    .entity("Error deleting quiz")
                    .build();
        }
    }


}

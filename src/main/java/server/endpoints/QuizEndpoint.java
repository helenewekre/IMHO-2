package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.Config;
import server.controller.MainController;
import server.controller.UserController;
import server.dbmanager.DbManager;
import server.models.Quiz;
import server.models.User;
import server.utility.Crypter;
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
    Config config = new Config();
    Crypter crypter = new Crypter();


    @GET
    @Path("/{CourseID}")
    public Response loadQuizzes(@PathParam("CourseID") int courseId) {
        ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);

        if (config.getEncryption()) {
            String newQuizzes = new Gson().toJson(quizzes);
            newQuizzes = crypter.encryptAndDecryptXor(newQuizzes);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(newQuizzes))
                    .build();

        }

        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(quizzes))
                .build();

    }
        // Method for creating a quiz
    @POST
    public Response createQuiz(@HeaderParam("authorization") String token, String quizJson) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.isAdmin()) {

            Boolean quizCreated = adminController.createQuiz(quizJson);

            return Response.status(200).type("application/json")
                    .entity("{\"quizCreated\":\"true\"}")
                    .build();
        }
        return Response
                .status(403)
                .type("application/json")
                .entity("{\"error\":\"no permissions\"}")
                .build();


    }
        // Method for deleting a quiz and all it's sub-tables
    @DELETE
    @Path("{deleteId}")
    public Response deleteQuiz(@PathParam("deleteId")int quizJson) {

        Boolean quizDeleted = adminController.deleteQuiz(quizJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizDeleted\":\"true\"}")
                .build();
    }


}

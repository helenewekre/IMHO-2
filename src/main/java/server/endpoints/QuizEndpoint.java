package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.controller.UserController;
import server.dbmanager.DbManager;
import server.models.Quiz;
import server.models.User;
import server.utility.Crypter;
import server.utility.CurrentUserContext;
import server.utility.Globals;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/quiz")
public class QuizEndpoint {
    DbManager dbManager = new DbManager();
    AdminController adminController = new AdminController();
    MainController mainController = new MainController();
    Crypter crypter = new Crypter();


    @GET
    @Path("/{CourseID}")
    public Response loadQuizzes(@HeaderParam("authorization") String token, @PathParam("CourseID") int courseId) throws SQLException {
        Globals.log.writeLog(this.getClass().getName(), this, "Quizzes loaded", 2);

        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);
            String newQuizzes = new Gson().toJson(quizzes);
            newQuizzes = crypter.encryptAndDecryptXor(newQuizzes);
            if (quizzes != null) {
                return Response.status(200).type("application/json").entity(new Gson().toJson(newQuizzes)).build();
            } else {
                return Response.status(200).type("application/json").entity("No quiz found").build();
            }
        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }

    // Method for creating a quiz
    @POST
    public Response createQuiz(@HeaderParam("authorization") String token, String quizJson) throws SQLException {
        Globals.log.writeLog(this.getClass().getName(), this, "Quiz created", 2);

        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            if (context.isAdmin()) {
                Quiz quizCreated = adminController.createQuiz(quizJson);
                String newQuiz = new Gson().toJson(quizCreated);
                newQuiz = crypter.encryptAndDecryptXor(newQuiz);
                return Response.status(200).type("application/json")
                        .entity(new Gson().toJson(newQuiz))
                        .build();
            } else {
                return Response
                        .status(403)
                        .type("application/json")
                        .entity("Error creating quiz")
                        .build();
            }
        } else {
            return Response
                    .status(403)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }


    // Method for deleting a quiz and all it's sub-tables
    @DELETE
    @Path("{deleteId}")
    public Response deleteQuiz(@HeaderParam("authorization") String token, @PathParam("deleteId")int quizJson) throws SQLException {
        Globals.log.writeLog(this.getClass().getName(), this, "Quiz deleted", 2);

        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            Boolean quizDeleted = adminController.deleteQuiz(quizJson);
            if (quizDeleted = true) {
                return Response
                        .status(200)
                        .type("text/plain")
                        .entity("Quiz is deleted")
                        .build();
            } else {
                return Response
                        .status(500)
                        .type("text/plain")
                        .entity("Error deleting quiz")
                        .build();
            }
        } else {
            return Response
                    .status(403)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }

}

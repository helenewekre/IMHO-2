package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.Quiz;
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
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null) {
            ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);
            String loadedQuizzes = new Gson().toJson(quizzes);
            loadedQuizzes = crypter.encryptAndDecryptXor(loadedQuizzes);
            Globals.log.writeLog(this.getClass().getName(), this, "Quizzes loaded", 2);

            if (quizzes != null) {
                return Response.status(200).type("application/json").entity(new Gson().toJson(loadedQuizzes)).build();
            } else {
                return Response.status(204).type("text/plain").entity("No quizzes").build();
            }
        } else {
            Globals.log.writeLog(this.getClass().getName(), this, "Quizzes failed loading", 2);
            return Response.status(401).type("text/plain").entity("Unauthorized").build();
        }
    }

    @POST
    // Method for creating a quiz
    public Response createQuiz(@HeaderParam("authorization") String token, String quiz) throws SQLException {
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null && currentUser.isAdmin()) {
            Quiz quizCreated = adminController.createQuiz(quiz);
            String newQuiz = new Gson().toJson(quizCreated);
            newQuiz = crypter.encryptAndDecryptXor(newQuiz);

            if (quizCreated != null) {
                Globals.log.writeLog(this.getClass().getName(), this, "Quiz created", 2);
                return Response.status(200).type("application/json").entity(new Gson().toJson(newQuiz)).build();
            } else {
                return Response.status(400).type("text/plain").entity("Failed creating quiz").build();
            }
        } else {
            return Response.status(401).type("text/plain").entity("Unauthorized").build();
        }
    }


    @DELETE
    @Path("{deleteId}")
    // Method for deleting a quiz and all it's sub-tables
    public Response deleteQuiz(@HeaderParam("authorization") String token, @PathParam("deleteId") int quizId) throws SQLException {
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null && currentUser.isAdmin()) {
            Boolean quizDeleted = adminController.deleteQuiz(quizId);
            if (quizDeleted = true) {
                Globals.log.writeLog(this.getClass().getName(), this, "Quiz deleted", 2);
                return Response.status(200).type("text/plain").entity("Quiz deleted").build();
            } else {
                return Response.status(400).type("text/plain").entity("Error deleting quiz").build();
            }
        } else {
            return Response.status(401).type("text/plain").entity("Unauthorized").build();
        }
    }

}

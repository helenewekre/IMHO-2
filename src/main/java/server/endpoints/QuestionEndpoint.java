package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.Question;
import server.utility.CurrentUserContext;
import server.utility.Crypter;
import server.utility.Globals;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();
    MainController mainController = new MainController();
    Crypter crypter = new Crypter();

    //GET method for loading questions bc. SQL statements is SELECT.
    @GET
    @Path("/{QuizId}")
    public Response loadQuestion(@HeaderParam("authorization") String token, @PathParam("QuizId") int quizId) throws SQLException {
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null) {
            ArrayList<Question> questions = adminController.loadQuestions(quizId);
            String loadedQuestions = new Gson().toJson(questions);
            loadedQuestions = crypter.encryptAndDecryptXor(loadedQuestions);

            if (questions != null) {
                Globals.log.writeLog(this.getClass().getName(), this, "Questions loaded", 2);
                return Response.status(200).type("application/json").entity(new Gson().toJson(loadedQuestions)).build();
            } else {
                return Response.status(204).type("application/json").entity("No questions").build();
            }
        } else {
            return Response.status(401).type("text/plain").entity("Unauthorized").build();
        }
    }

    @POST
    //Method for creating a question
    public Response createQuestion(@HeaderParam("authorization") String token, String question) throws SQLException {
        CurrentUserContext currentUser = mainController.getUserFromTokens(token);

        if (currentUser.getCurrentUser() != null && currentUser.isAdmin()) {
            Question questionCreated = adminController.createQuestion(new Gson().fromJson(question, Question.class));
            String newQuestion = new Gson().toJson(questionCreated);
            newQuestion = crypter.encryptAndDecryptXor(newQuestion);

            if (questionCreated != null) {
                Globals.log.writeLog(this.getClass().getName(), this, "Question created", 2);
                return Response.status(200).type("application/json").entity(new Gson().toJson(newQuestion)).build();
            } else {
                return Response.status(400).type("application/json").entity("Failed creating question").build();
            }
        } else {
            return Response.status(401).type("application/json").entity("Unauthorized").build();

        }
    }


}

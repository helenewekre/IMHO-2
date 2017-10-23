package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Question;
import server.utility.CurrentUserContext;
import server.utility.Crypter;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();
    MainController mainController = new MainController();

    Crypter crypter = new Crypter();

    //Method for creating a question
    @POST
    public Response createQuestion(@HeaderParam("authorization") String token, String questionJson) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if(context.getCurrentUser() != null) {
            if(context.isAdmin()) {
                Question questionCreated = adminController.createQuestion(new Gson().fromJson(questionJson, Question.class));
                String newQuestion = new Gson().toJson(questionCreated);
                newQuestion = crypter.encryptAndDecryptXor(newQuestion)
                if (questionCreated != null) {
                    return Response
                            .status(200)
                            .type("application/json")
                            .entity(new Gson().toJson(newQuestion))
                            .build();
                } else {
                    return Response
                            .status(200)
                            .type("application/json")
                            .entity("Could not create question")
                            .build();
                }
            } else {
                return Response
                        .status(200)
                        .type("application/json")
                        .entity("You are not authorized")
                        .build();
            }
        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }

    //GET method for loading questions bc. SQL statements is SELECT.
    @GET
    @Path("/{quizId}")
    public Response loadQuestion(@HeaderParam("authorization") String token, @PathParam("quizId") int quizId) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            //Instance of dbmanager to get access to loadQuestions method
            DbManager dbManager = new DbManager();
            //New arraylist, gives it the value of the questions loaded in loadQuestions (dbmanager), takes the integer quizId as param
            ArrayList<Question> questions = dbManager.loadQuestions(quizId);
            String newQuestions = new Gson().toJson(questions);
            newQuestions = crypter.encryptAndDecryptXor(newQuestions);
            if(questions != null) {
                //Returning as Json
                return Response.status(200).type("application/json").entity(new Gson().toJson(newQuestions)).build();
            } else {
                return Response.status(200).type("application/json").entity("No question found").build();
            }
        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }
}

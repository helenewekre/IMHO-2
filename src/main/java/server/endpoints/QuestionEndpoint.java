package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Question;
import server.utility.Crypter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();
    Crypter crypter = new Crypter();

    //Method for creating a question
    @POST
    public Response createQuestion(String questionJson) {

        Question question = new Gson().fromJson(questionJson, Question.class);
        adminController.createQuestion(question);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"questionCreated\":\"true\"}")
                .build();
    }

    //GET method for loading questions bc. SQL statements is SELECT.
    @GET
    @Path("/{quizId}")
    public Response loadQuestions(@PathParam("quizId") int quizId) {

        //Instance of dbmanager to get access to loadQuestions method
        DbManager dbManager = new DbManager();
        //New arraylist, gives it the value of the questions loaded in loadQuestions (dbmanager), takes the integer quizId as param
        ArrayList<Question> questions = dbManager.loadQuestions(quizId);

            String newQuestions = new Gson().toJson(questions);
            newQuestions = crypter.encryptAndDecryptXor(newQuestions);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(newQuestions))
                    .build();
        }



}

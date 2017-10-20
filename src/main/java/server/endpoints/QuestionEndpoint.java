package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.dbmanager.DbManager;
import server.models.Question;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();


    //Method for creating a question
    @POST
    public Response createQuestion(String questionJson) {
        Question question = new Gson().fromJson(questionJson, Question.class);

        Question questionCreated = adminController.createQuestion(question);

        if (questionCreated != null) {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(questionCreated))
                    .build();
        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Could not create question")
                    .build();
        }
    }

    //GET method for loading questions bc. SQL statements is SELECT.
    @GET
    @Path("/{quizId}")
    public Response loadQuestions(@PathParam("quizId") int quizId) {

        //Instance of dbmanager to get access to loadQuestions method
        DbManager dbManager = new DbManager();
        //New arraylist, gives it the value of the questions loaded in loadQuestions (dbmanager), takes the integer quizId as param
        ArrayList<Question> questions = dbManager.loadQuestions(quizId);

        //Returning as Json
        return Response.status(200).type("application/json").entity(new Gson().toJson(questions)).build();
    }
}

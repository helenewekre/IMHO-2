package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.models.Question;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();


    //Method for creating a question
    @POST
    public Response createQuestion(String questionJson) {

        Question question = new Gson().fromJson(questionJson, Question.class);
        Boolean questionCreated = adminController.createQuestion(question);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"questionCreated\":\"true\"}")
                .build();
    }
}

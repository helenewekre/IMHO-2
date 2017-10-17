package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.dbmanager.dbmanager2;
import server.models.Question;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/question")
public class QuestionEndpoint {
    AdminController adminController = new AdminController();

    @POST
    public Response createQuestion(String questionJson) {

        Boolean questionCreated = adminController.createQuestion(questionJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizCreated\":\"true\"}")
                .build();
    }
}

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

    @POST
    public Response createQuestion(String questionJson) {
        Boolean questionCreated = adminController.createQuestion(questionJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"questionCreated\":\"true\"}")
                .build();
    }

    @GET
    @Path("/{quizId}/options")
    public Response loadQuestions(@PathParam("quizId") int quizId) {

        DbManager dbManager = new DbManager();
        Question question = dbManager.loadQuestion(quizId);
        return Response.status(200).type("application/json").entity(new Gson().toJson(question)).build();
    }
}

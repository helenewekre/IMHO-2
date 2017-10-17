package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager2;
import server.models.Question;
import server.models.Quiz;
import server.controller.AdminController;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/quiz")
public class QuizEndpoint {
    AdminController adminController = new AdminController();

    @POST
    public Response createQuiz(String quizJson) {

        Boolean quizCreated = adminController.createQuiz(quizJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizCreated\":\"true\"}")
                .build();
    }



}

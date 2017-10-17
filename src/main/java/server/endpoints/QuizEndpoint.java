package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager2;
import server.models.Quiz;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/quiz")
public class QuizEndpoint {

    @GET
    public Response createQuiz() {

        dbmanager2 db2 = new dbmanager2();
        Quiz quiz = new Quiz("Christian", 3, "Den gode quiz", "En quiz om at lave quizzer", 2);
        db2.createQuiz(quiz);
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(quiz))
                .build();
    }


}

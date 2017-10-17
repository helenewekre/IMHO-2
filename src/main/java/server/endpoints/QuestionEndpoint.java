package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager2;
import server.models.Question;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/question")
public class QuestionEndpoint {

    @POST
    public Response createQuestion(String questionJson) {

        Question question = new Gson().fromJson(questionJson, Question.class);

        dbmanager2 db2 = new dbmanager2();
        db2.createQuestion(question);
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(question))
                .build();
    }
}

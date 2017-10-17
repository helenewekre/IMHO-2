package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager2;
import server.models.Question;
import server.models.Quiz;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/quiz")
public class QuizEndpoint {

    @POST
    public Response createQuiz(String quizJson) {

        Quiz quiz = new Gson().fromJson(quizJson, Quiz.class);

        dbmanager2 db2 = new dbmanager2();

        db2.createQuiz(quiz);
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(quiz))
                .build();
    }

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

package server.endpoints;
import com.google.gson.Gson;
import server.dbmanager.dbmanager3;
import server.models.Quiz;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/quizselection")
public class QuizEndpoint {
    dbmanager3 dbmanager = new dbmanager3();

    @GET
    public Response loadQuizzes(int courseId){
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

        return Response.status(200).entity(new Gson().toJson(quizzes)).build();

    }

}

package server.endpoints;
import com.google.gson.Gson;
import server.dbmanager.dbmanager3;
import server.models.Quiz;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class OptionEndpoint {
    dbmanager3 dbamanager3 = new dbmanager3();

    @GET
    @Path("{quizId}")
    public Response showQuiz(@PathParam("quizId") Integer quizId){
        Quiz quizFound = dbmanager3.showQuiz(quizId);
        return Response.status(200).entity(new Gson().toJson(quizFound)).build();

    }


}

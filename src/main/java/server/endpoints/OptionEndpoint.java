package server.endpoints;
import com.google.gson.Gson;
import server.dbmanager.dbmanager3;
import server.models.Quiz;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/quiz")
public class OptionEndpoint {

    dbmanager3 dbmanager = new dbmanager3();

    //Without neccesary parameter! Must be fixed
    @GET
    public Response loadQuestions(Integer quizId){

        ArrayList<Quiz> quizFound = new ArrayList<Quiz>();

        return Response.status(200).entity(new Gson().toJson(quizFound)).build();

    }


}

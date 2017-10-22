package server.endpoints;

import com.google.gson.Gson;
import server.controller.UserController;
import server.utility.Globals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/result")
public class ResultEndpoint {

    UserController userController = new UserController();

    @GET
    //send totale number of correct answer and questions back to the user.
    @Path("{quizID}/{userID}")
    public Response getUserScore(@PathParam("quizID") int quizID, @PathParam("userID") int userID){
        Globals.log.writeLog(getClass().getName(), this, "Get result", 2);


        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson( userController.getResult(quizID,userID)))
                .build();

    }
}

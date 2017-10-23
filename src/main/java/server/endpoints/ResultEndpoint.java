package server.endpoints;

import com.google.gson.Gson;
import server.controller.MainController;
import server.controller.UserController;
import server.models.Result;
import server.utility.Crypter;
import server.utility.CurrentUserContext;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/result")
public class ResultEndpoint {
    UserController userController = new UserController();
    MainController mainController = new MainController();
    Crypter crypter = new Crypter();


    @GET
    //send totale number of correct answer and questions back to the user.
    @Path("{quizID}/{userID}")
    public Response getUserScore(@HeaderParam("authorization") String token, @PathParam("quizID") int quizID, @PathParam("userID") int userID) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            if(!context.isAdmin()) {
                Result result = userController.getResult(quizID, userID);
                String myResult = new Gson().toJson(result);
                myResult = crypter.encryptAndDecryptXor(myResult);
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(new Gson().toJson(myResult))
                        .build();
            } else {
                return Response
                        .status(200)
                        .type("application/json")
                        .entity("You are not authorized")
                        .build();
            }

        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }


    }
}

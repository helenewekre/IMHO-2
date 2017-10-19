package server.endpoints;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.User;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

@Path("/user")
public class UserEndpoint {
    //Creating objects of database manager and MainController
    DbManager dbManager = new DbManager();
    MainController mainController = new MainController();
    User currentUser = new User();

    @POST
    @Path("/login")
    //Authorizing a user
    public Response authorizeUser(String user) {
        User userAuth = new Gson().fromJson(user, User.class);
        String token = mainController.authUser(userAuth);

        return Response.status(200).entity(new Gson().toJson(token)).build();
    }

    @POST
    @Path("/signup")
    //Creating a new user
    public Response createUser(String user) {
        Boolean userCreated = mainController.createUser(user);
        return Response.status(200).type("application/json").entity("{\"userCreated\":\"true\"}").build();
    }



    @Path("/profile")
    @GET
    public Response get(@HeaderParam("authorization") String token, @PathParam("id") int idUser) throws SQLException {
        User myUser = mainController.getUserFromTokens(token);

        if (myUser != null) {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(myUser))
                    .build();

        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Fejl")
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logOut(String idUser) throws SQLException {
        int id = new Gson().fromJson(idUser, Integer.class);

        boolean isOut = dbManager.deleteToken(id);
            return Response.status(200).entity(isOut).build();

    }
}
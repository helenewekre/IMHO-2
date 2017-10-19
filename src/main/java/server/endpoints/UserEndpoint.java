package server.endpoints;


import com.google.gson.Gson;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.User;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/user")
public class UserEndpoint {
    //Creating objects of database manager and MainController
    DbManager dbManager = new DbManager();
    MainController mainController = new MainController();

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
    public Response get(@HeaderParam("authorization") String token) throws SQLException {
        User myUser = mainController.getUserFromTokens(token);
        myUser.getType();


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
package server.endpoints;


import com.google.gson.Gson;
import server.controller.MainController;
import server.dbmanager.DbManager;
import server.models.User;
import server.utility.CurrentUserContext;

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
        User authorizedUser = mainController.authUser(userAuth);

        if (authorizedUser != null) {
            return Response.status(200).entity(new Gson().toJson(authorizedUser)).build();
        } else {
            return Response.status(500).entity("There was an error").build();
        }
    }

    @POST
    @Path("/signup")
    //Creating a new user
    public Response createUser(String user) {
        User userCreated = mainController.createUser(user);

        if (userCreated != null) {
            return Response.status(200).type("application/json").entity(new Gson().toJson(userCreated)).build();
        } else {
            return Response.status(500).type("application/json").entity("Could not create user").build();
        }
    }


    @Path("/profile")
    @GET
    //Getting own profile by token
    public Response get(@HeaderParam("authorization") String token) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if (context.getCurrentUser() != null) {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(context.getCurrentUser()))
                    .build();
        } else {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("Error loading profile")
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logOut(String idUser) throws SQLException {
        int id = new Gson().fromJson(idUser, Integer.class);
        if(dbManager.deleteToken(id) == true) {
            return Response.status(200).entity("You are now logged out").build();
        } else {
            return Response.status(500).entity("There was an error").build();
        }


    }
}
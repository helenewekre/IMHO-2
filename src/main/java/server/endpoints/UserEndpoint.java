package server.endpoints;

import com.google.gson.Gson;
import server.controller.MainController;
import server.dbmanager.dbmanager1;
import server.models.User;
import server.utility.Digester;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserEndpoint {
    //Creating objects of database manager and MainController
    dbmanager1 dbmanager1 = new dbmanager1();
    MainController mainController = new MainController();

    @POST
    @Path("/login")
    /*
    Endpoint for authorizing a user.
    A user String is given to the maincontroller
    which handles the logic. Returning a string with the found user.
     */
    public Response authorizeUser(String user) {
        String userFound = mainController.authUser(user);
        return Response.status(200).entity(userFound).build();
    }

    @POST
    @Path("/signup")
    /*
    Endpoint for creating a user.
    A user String is given to the maincontroller
    which handles the logic. Returning a boolean, which decides if
    the user is created or not.
     */
    public Response createUser(String user) {
        Boolean userCreated = mainController.createUser(user);
        return Response.status(200).type("application/json").entity("{\"userCreated\":\"true\"}").build();
    }

    @GET
    //Test method for user path
    public Response get() {
        System.out.println("hallo!");
        return Response.status(200).entity("User").build();


    }

}
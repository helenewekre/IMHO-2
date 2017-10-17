package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager1;
import server.controller.MainController;
import server.dbmanager.dbmanager4;
import server.models.User;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserEndpoint {
    //Creating objects of database manager and MainController
    dbmanager1 dbmanager1 = new dbmanager1();
    dbmanager4 dbmanager4 = new dbmanager4();
    MainController mainController = new MainController();
    User currentUser = new User();


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
    // User ID as a part of the PATH
    @Path("{id}")
    public Response getUserProfile(@PathParam("id") int id){

        //Creates a currentuser from a user ID, which is logged in.
        currentUser = dbmanager4.getUserProfile(id);

        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(currentUser))
                .build();

    }

}
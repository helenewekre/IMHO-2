package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.dbmanager1;
import server.models.User;
import server.utility.Digester;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/user")
public class UserEndpoint {

    dbmanager1 dbmanager1 = new dbmanager1();

    @POST
    @Path("{username}/{password}")
    public Response authorizeUser(@PathParam("username") String username, @PathParam("password") String password) {
        User userFound = dbmanager1.authorizeUser(username, password);
        return Response.status(200).entity(new Gson().toJson(userFound)).build();

    }
    @POST
    public Response createUser(String user) {

        Digester digester = new Digester();

        User newUser = new Gson().fromJson(user, User.class);

        newUser.setPassword(digester.hashWithSalt(newUser.getPassword()));

        dbmanager1.createUser(newUser);

        return Response.status(200).type("application/json").entity("{\"userCreated\":\"true\"}").build();
    }

    @GET
    public Response get() {
        System.out.println("hallo!");

        return Response.status(200).entity("User").build();


    }

}
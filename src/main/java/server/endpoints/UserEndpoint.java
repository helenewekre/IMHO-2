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
    @Path("/login")
    public Response authorizeUser(String user) {

        User newUser = new Gson().fromJson(user, User.class);

        User findUser = dbmanager1.authorizeUser(newUser.getUsername(), newUser.getPassword());

        String userFound = new Gson().toJson(findUser, User.class);

        return Response.status(200).entity(userFound).build();

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
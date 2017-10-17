package server.endpoints;

import com.google.gson.Gson;
import server.database.UserTable;
import server.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/users")
public class UserEndpoint {

    @GET
    public Response getUsers(){

        UserTable userTable = UserTable.getInstance();
        ArrayList<User> users = userTable.getUsers();

        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(users))
                .build();
    }

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") int id){

        //Lidt hjælp
        //
        UserTable userTable = UserTable.getInstance();
        User foundUser = userTable.findById(id);

        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(foundUser))
                .build();
    }

    @POST
    public Response createUser(String jsonUser) {

        UserTable userTable = UserTable.getInstance();
        User newUser = new Gson().fromJson(jsonUser, User.class);
        userTable.addUser(newUser);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"userCreated\":\"true\"}")
                .build();
    }

}

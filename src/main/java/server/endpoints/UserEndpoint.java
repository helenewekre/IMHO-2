package server.endpoints;

import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import server.dbmanager.dbmanager1;
import server.dbmanager.dbmanager4;
import server.models.User;
import server.utility.Digester;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/user")
public class UserEndpoint {

    dbmanager1 dbmanager1 = new dbmanager1();
    private static dbmanager4 dbmanager4 = new dbmanager4();
    User currentUser = new User();



    @POST
    @Path("{username}/{password}")
    public Response authorizeUser(@PathParam("username") String username, @PathParam("password") String password) {
        User userFound = dbmanager1.authorizeUser(username, password);
        return Response.status(200).entity(new Gson().toJson(userFound)).build();

    }
    //@POST
    // public Response createUser(user)

    @GET
    public Response get() {
        System.out.println("hallo!");

        return Response.status(200).entity("User").build();

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
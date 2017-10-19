package server.endpoints;

import com.google.gson.Gson;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import server.dbmanager.DbManager;
import server.models.User;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Path("/login")
public class LoginEndpoint {
    DbManager dbManager = new DbManager();

    @POST
    /*
    Endpoint for authorizing a user.
    A user String is given to the maincontroller
    which handles the logic. Returning a string with the found user.
     */
    public Response authorizeUser(String user) {
        User userAuth = new Gson().fromJson(user, User.class);
        User authorizedUser = dbManager.authorizeUser(userAuth.getUsername(), userAuth.getPassword());

        String token = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256("Secret");
            long timeValue = (System.currentTimeMillis()*1000)+20000205238L;
            Date expDate = new Date(timeValue);

            token = JWT.create().withClaim("User", authorizedUser.getUsername()).withExpiresAt(expDate).withIssuer("IMHO").sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(token).build();
    }
}

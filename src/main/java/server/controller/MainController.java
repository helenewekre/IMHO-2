package server.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.User;
import server.utility.CurrentUserContext;
import server.utility.Digester;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;


public class MainController {
    private DbManager dbManager;
    private Digester digester;


    //The constructor for instantation
    public MainController() {
        dbManager = new DbManager();
        digester = new Digester();

    }

    // Logic behind authorizing user
    public String authUser(User user) {
        String token = null;
        User foundUser = dbManager.getTimeCreatedByUsername(user.getUsername());
        user.setPassword(digester.hashWithSalt(user.getPassword() + foundUser.getTimeCreated()));
        User authorizedUser = dbManager.authorizeUser(user.getUsername(), user.getPassword());

        try {
            Algorithm algorithm = Algorithm.HMAC256("Secret");
            long timeValue = (System.currentTimeMillis() * 1000) + 20000205238L;
            Date expDate = new Date(timeValue);

            token = JWT.create().withClaim("User", authorizedUser.getUsername()).withExpiresAt(expDate).withIssuer("IMHO").sign(algorithm);
            dbManager.addToken(token, authorizedUser.getIdUser());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (token != null) {
            return token;
        } else {
            return null;
        }
    }

    //Logic behind creating user.

    public Boolean createUser(String user) {
        User userCreated = new Gson().fromJson(user, User.class);

        long unixTime = (long) Math.floor(System.currentTimeMillis() / 10000);

        userCreated.setTimeCreated(unixTime);
        userCreated.setPassword(digester.hashWithSalt(userCreated.getPassword()+userCreated.getTimeCreated()));

        return dbManager.createUser(userCreated);
    }

    public CurrentUserContext getUserFromTokens(String token) throws SQLException {
        User user = dbManager.getUserFromToken(token);
        CurrentUserContext context = new CurrentUserContext();
        context.setCurrentUser(user);
        return context;
    }

}




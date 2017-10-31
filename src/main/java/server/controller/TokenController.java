package server.controller;

import server.dbmanager.DbManager;
import server.models.User;
import server.utility.CurrentUserContext;

import java.sql.SQLException;

public class TokenController {


    public TokenController() {

    }


    //Method to save the user object in a currentUser
    public CurrentUserContext getUserFromTokens(String token) throws SQLException {
        DbManager dbManager = new DbManager();
        User user = dbManager.getUserFromToken(token);
        CurrentUserContext context = new CurrentUserContext();
        context.setCurrentUser(user);
        return context;
    }

    //Delete the token at log out
    public boolean deleteToken(int userId) throws SQLException {
        DbManager dbManager = new DbManager();
        boolean tokenDeleted = dbManager.deleteToken(userId);
        return tokenDeleted;
    }
}

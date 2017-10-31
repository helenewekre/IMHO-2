package server.controller;

import server.dbmanager.DbManager;
import server.models.User;
import server.utility.CurrentUserContext;

import java.sql.SQLException;

public class TokenController {
    private DbManager dbManager;


    public TokenController() {
        dbManager = new DbManager();
    }


    //Method to save the user object in a currentUser
    public CurrentUserContext getUserFromTokens(String token) throws SQLException {
        User user = dbManager.getUserFromToken(token);
        CurrentUserContext context = new CurrentUserContext();
        context.setCurrentUser(user);
        return context;
    }

    //Delete the token at log out
    public boolean deleteToken(int userId) throws SQLException {
        boolean tokenDeleted = dbManager.deleteToken(userId);
        return tokenDeleted;
    }
}

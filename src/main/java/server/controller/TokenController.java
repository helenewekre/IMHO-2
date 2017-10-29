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


    public CurrentUserContext getUserFromTokens(String token) throws SQLException {
        User user = dbManager.getUserFromToken(token);
        CurrentUserContext context = new CurrentUserContext();
        context.setCurrentUser(user);
        return context;
    }

    public boolean deleteToken(int userId) throws SQLException {
        boolean tokenDeleted = dbManager.deleteToken(userId);
        if (tokenDeleted = true) {
            return true;
        } else {
            return false;
        }

    }
}

package server.utility;

import server.models.User;

import java.sql.SQLException;

public class CurrentUserContext {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Boolean isAdmin() {
        if(this.currentUser.getType() == 1) {
            return true;
        } else {
            return false;
        }
    }
}

package server.controller;

import server.models.User;
import server.dbmanager.dbmanager1;

import java.util.Scanner;


public class MainController {
    private AdminController adminController;
    private UserController userController;
    private User currentUser;
    private Scanner input;
    dbmanager1 dbmanager1 = new dbmanager1();



    public MainController() {

    }

    public boolean authUser(String username, String password) {
        User user = new User();

        user = dbmanager1.authorizeUser(username, password);

        if(user!=null) {
            user = currentUser;
            return true;

        }
        else {
            return false;
        }

    }

    public boolean createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return dbmanager1.createUser(user);
    }



}
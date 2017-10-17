package server.controller;

import com.google.gson.Gson;
import server.models.User;
import server.dbmanager.dbmanager1;
import server.utility.Digester;

import java.util.Scanner;


public class MainController {
    private AdminController adminController;
    private UserController userController;
    private User currentUser;
    private Scanner input;
    private dbmanager1 dbmanager1;
    private Digester digester;


    //The constructor for instantation
    public MainController() {
        dbmanager1 = new dbmanager1();
        digester = new Digester();

    }

    // Logic behind authorizing user
    public String authUser(String user) {
        User userAuth = new Gson().fromJson(user, User.class);
        User authorizedUser = dbmanager1.authorizeUser(userAuth.getUsername(), userAuth.getPassword());
        String userFound = new Gson().toJson(authorizedUser, User.class);

        if (userFound != null) {
            return userFound;
        } else {
            return null;
        }
    }

    //Logic behind creating user.
    public Boolean createUser(String user) {
        User userCreated = new Gson().fromJson(user, User.class);
        userCreated.setPassword(digester.hashWithSalt(userCreated.getPassword()));
        Boolean ifCreated = dbmanager1.createUser(userCreated);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }
    }



}
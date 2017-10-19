package server.controller;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.User;
import server.utility.Digester;
import server.utility.Globals;

import java.util.Scanner;


public class MainController {
    private AdminController adminController;
    private UserController userController;
    private User currentUser;
    private Scanner input;
    private DbManager dbManager;
    private Digester digester;


    //The constructor for instantation
    public MainController() {
        dbManager = new DbManager();
        digester = new Digester();

    }

    // Logic behind authorizing user
    public String authUser(String user) {
        User userAuth = new Gson().fromJson(user, User.class);
        User authorizedUser = dbManager.authorizeUser(userAuth.getUsername(), userAuth.getPassword());
        String userFound = new Gson().toJson(authorizedUser, User.class);

        if (userFound != null) {
            Globals.log.writeLog(getClass().getName(), this, "Auth. user", 2);
            return userFound;
        } else {
            return null;
        }
    }

    //Logic behind creating user.
    public Boolean createUser(String user) {
        User userCreated = new Gson().fromJson(user, User.class);
        userCreated.setPassword(digester.hashWithSalt(userCreated.getPassword()));
        Boolean ifCreated = dbManager.createUser(userCreated);


        if(ifCreated) {
            Globals.log.writeLog(getClass().getName(), this, "User created", 2);
            return true;
        } else {
            return false;
        }
    }



}
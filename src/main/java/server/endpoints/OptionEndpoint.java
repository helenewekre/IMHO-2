package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.MainController;
import server.controller.UserController;
import server.models.Option;
import server.utility.CurrentUserContext;
import server.utility.Crypter;
import server.utility.Globals;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

//Specifies path
    @Path("/option")
    public class OptionEndpoint {
    AdminController adminController = new AdminController();
    UserController userController = new UserController();
    MainController mainController = new MainController();
    Crypter crypter = new Crypter();

    @POST
    //Creating a new option for a quiz.
    public Response createOption(@HeaderParam("authorization") String token, String option) throws SQLException {
        CurrentUserContext context = mainController.getUserFromTokens(token);

        if(context.getCurrentUser() != null && context.isAdmin()) {
                Option optionCreated = adminController.createOption(option);
                String newOption = new Gson().toJson(optionCreated);
                newOption = crypter.encryptAndDecryptXor(newOption);
                if (optionCreated != null) {
                    Globals.log.writeLog(this.getClass().getName(), this, "Option created", 2);
                    return Response.status(200).type("application/json").entity(new Gson().toJson(newOption)).build();
                } else {
                    return Response.status(500).type("application/json").entity("Error in creating option").build();
            }
        } else {
            return Response.status(500).type("application/json").entity("Error loading profile").build();
        }
    }

    @DELETE
    @Path("{deleteId}")
    public Response deleteAnswer(@HeaderParam("authorization") String token, @PathParam("deleteId") int answerJson) throws SQLException {
        Globals.log.writeLog(this.getClass().getName(), this, "Answer deleted", 2);

        CurrentUserContext context = mainController.getUserFromTokens(token);
        if(context.getCurrentUser() != null) {
            if(context.isAdmin()) {
                Boolean quizDeleted = userController.deleteAnswer(answerJson);
                return Response.status(200).type("application/json").entity(new Gson().toJson(quizDeleted)).build();
            } else {
                return Response.status(500).type("application/json").entity("You are not authorized").build();
            }
        } else {
            return Response.status(500).type("application/json").entity("Error loading profile").build();
        }
    }

    @GET
        //Specifies path
        @Path("/{question_id}")
        public Response loadOptions (@HeaderParam("authorization") String token, @PathParam("question_id") int questionId) throws SQLException {
        Globals.log.writeLog(this.getClass().getName(), this, "Options loaded", 2);

        CurrentUserContext context = mainController.getUserFromTokens(token);

        if(context.getCurrentUser() != null) {
            //Instance of dbmanager to get access to loadOptions method
            //New arraylist of Option objects. Gives arraylist the value of the options loaded in loadOptions (dbmanager)
            ArrayList options = userController.getOptions(questionId);
            String newLoadedOptions = new Gson().toJson(options);
            newLoadedOptions = crypter.encryptAndDecryptXor(newLoadedOptions);
            //Returns options object in arraylist as json
            if (options != null) {
                return Response.status(200).type("application/json").entity(new Gson().toJson(newLoadedOptions)).build();
            } else {
                return Response.status(200).type("application/json").entity("No options").build();
            }
        } else {
            return Response.status(500).type("application/json").entity("Error loading profile").build();

        }
    }
}

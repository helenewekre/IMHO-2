package server.endpoints;

import com.google.gson.Gson;
import server.controller.AdminController;
import server.controller.Config;
import server.controller.UserController;
import server.dbmanager.DbManager;
import server.models.Option;
import server.utility.Crypter;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Specifies path
    @Path("/option")
    public class OptionEndpoint {
    AdminController adminController = new AdminController();
    UserController userController = new UserController();
    Crypter crypter = new Crypter();
    Config config = new Config();


    @POST
    public Response createOption(String optionJson) {
       // Boolean optionCreated = adminController.createOption(optionJson);
        //GET method for loading options bc. SQL statements is SELECT.
        Option option = new Gson().fromJson(optionJson, Option.class);
        adminController.createOption(option);


        if (config.getEncryption()) {

            String newOption = new Gson().toJson(option);
            newOption = crypter.encryptAndDecryptXor(newOption);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(newOption))
                    .build();

        }
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("{\"optionCreated\":\"true\"}")
                    .build();



    }
    @DELETE
    @Path("{deleteId}")
    public Response deleteAnswer(@PathParam("deleteId")int answerJson) {

        Boolean quizDeleted = userController.deleteAnswer(answerJson);

        return Response
                .status(200)
                .type("application/json")
                .entity("{\"quizDeleted\":\"true\"}")
                .build();
    }

    @GET
        //Specifies path
        @Path("/{question_id}")
        public Response loadOptions (@PathParam("question_id") int questionId){
            //Instance of dbmanager to get access to loadOptions method
            DbManager dbmanager = new DbManager();
            //New arraylist of Option objects. Gives arraylist the value of the options loaded in loadOptions (dbmanager)
            ArrayList<Option> options = dbmanager.loadOptions(questionId);

            if (config.getEncryption()) {
                String newOptions = new Gson().toJson(options);
                newOptions = crypter.encryptAndDecryptXor(newOptions);

                return Response.status(200)
                        .type("application/json")
                        .entity(new Gson().toJson(newOptions))
                        .build();

            }
                //Returns options object in arraylist as json
                return Response.status(200)
                        .type("application/json")
                        .entity(new Gson().toJson(options))
                        .build();
            }
        }

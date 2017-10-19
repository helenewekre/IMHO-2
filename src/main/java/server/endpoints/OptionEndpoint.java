package server.endpoints;

import com.google.gson.Gson;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Option;
import server.models.Quiz;
import server.utility.Crypter;
import server.utility.Globals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/option")
public class OptionEndpoint {

    DbManager dbmanager = new DbManager();
    Crypter crypter = new Crypter();
    Config config = new Config();

    @GET
    @Path("/{question_id}")
    public Response loadOptions (@PathParam("question_id") int questionId){
        Globals.log.writeLog(this.getClass().getName(), this, "Loaded options", 2);

        //IF-statement som afhænger af hvorvidt ENCRYPTION er sat til True eller False
        if (Config.getEncryption()) {
            ArrayList<Option> options = dbmanager.loadOptions(questionId);
            String newOptions = new Gson().toJson(options);
            newOptions = crypter.encryptAndDecryptXor(newOptions);
            newOptions = new Gson().toJson(newOptions);

            return Response.status(200).entity(newOptions).build();

        } else {
            ArrayList<Option> options = dbmanager.loadOptions(questionId);
            return Response.status(200).entity(new Gson().toJson(options)).build();


        }

    }
}





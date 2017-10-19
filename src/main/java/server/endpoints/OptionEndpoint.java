package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Option;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Specifies path
    @Path("/option")
    public class OptionEndpoint {

        //GET method for loading options bc. SQL statements is SELECT.
        @GET
        //Specifies path
        @Path("/{question_id}")
        public Response loadOptions (@PathParam("question_id") int questionId){
            //Instance of dbmanager to get access to loadOptions method
            DbManager dbmanager = new DbManager();
            //New arraylist of Option objects. Gives arraylist the value of the options loaded in loadOptions (dbmanager)
            ArrayList<Option> options = dbmanager.loadOptions(questionId);

            //Returns options object in arraylist as json
            return Response.status(200).type("application/json").entity(new Gson().toJson(options)).build();


            }

        }

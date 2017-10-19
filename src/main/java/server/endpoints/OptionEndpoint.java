package server.endpoints;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Option;
import server.models.Question;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

    @Path("/option")
    public class OptionEndpoint {

        @GET
        @Path("/{question_id}")
        public Response loadOptions (@PathParam("question_id") int questionId){
            DbManager dbmanager = new DbManager();
            ArrayList<Option> options = dbmanager.loadOptions(questionId);

            return Response.status(200).type("application/json").entity(new Gson().toJson(options)).build();


            }

        }

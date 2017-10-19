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

package server.endpoints;

import com.google.gson.Gson;
import server.controller.Config;
import server.dbmanager.DbManager;
import server.models.Option;
import server.models.Quiz;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

    @Path("/option")
    public class OptionEndpoint {

        DbManager dbmanager = new DbManager();

        @GET
        @Path("/{question_id}")
        public Response loadOptions (@PathParam("question_id") int questionId){

                ArrayList<Option> options = dbmanager.loadOptions(questionId);
                return Response.status(200).entity(new Gson().toJson(options)).build();


            }

        }
    }


}


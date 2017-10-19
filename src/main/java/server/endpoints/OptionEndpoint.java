package server.endpoints;

import server.controller.AdminController;
import server.controller.UserController;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("/option")
public class OptionEndpoint {
    AdminController adminController = new AdminController();
    UserController userController = new UserController();

    @POST
    public Response createOption(String optionJson) {
        Boolean optionCreated = adminController.createOption(optionJson);

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

}

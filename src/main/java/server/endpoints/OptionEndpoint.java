package server.endpoints;

import server.controller.AdminController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/option")
public class OptionEndpoint {
    AdminController adminController = new AdminController();

    @POST
    public Response createOption(String optionJson) {
        Boolean optionCreated = adminController.createOption(optionJson);

        return Response
                    .status(200)
                    .type("application/json")
                    .entity("{\"optionCreated\":\"true\"}")
                    .build();
    }
}

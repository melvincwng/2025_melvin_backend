package iss.nus.edu.sg.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


@Path("healthCheck")
public class HealthCheckResource {

    @GET
    public Response healthCheck() {
        return Response.ok("API is working").build();
    }
}

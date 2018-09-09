package com.balaji.resources;

import com.balaji.authentication.User;
import com.balaji.resources.models.Message;
import io.dropwizard.auth.Auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/outbound")
@Produces(MediaType.APPLICATION_JSON)
public class OutboundSmsResource {

    @Path("/sms")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inbound(Message message) {
        return Response.ok(message).build();
    }
}

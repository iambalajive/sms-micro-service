package com.balaji.resources;

import com.balaji.interceptors.request.User;
import com.balaji.resources.models.Message;
import io.dropwizard.auth.Auth;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/inbound")
@Produces(MediaType.APPLICATION_JSON)
public class InboundSmsResource {

    @Path("/sms")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inbound(@Auth User user,@Valid Message message) {
        return Response.ok(message).build();
    }


}

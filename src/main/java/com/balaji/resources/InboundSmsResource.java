package com.balaji.resources;

import com.balaji.cache.StopMessageCache;
import com.balaji.dao.PhoneNumberDAO;
import com.balaji.dao.model.PhoneNumber;
import com.balaji.interceptors.request.User;
import com.balaji.resources.models.Message;
import com.balaji.resources.models.SmsResponse;
import io.dropwizard.auth.Auth;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/inbound")
@Produces(MediaType.APPLICATION_JSON)
public class InboundSmsResource {

    private StopMessageCache stopMessageCache;

    private PhoneNumberDAO phoneNumberDAO;

    public InboundSmsResource(StopMessageCache stopMessageCache, PhoneNumberDAO phoneNumberDAO) {
        this.stopMessageCache = stopMessageCache;
        this.phoneNumberDAO = phoneNumberDAO;
    }

    private boolean isStopMessage(String text) {
        String message = text.replaceAll("\\s{2,}", "").toLowerCase();
        return "stop".equals(message);
    }

    @Path("/sms")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inbound(@Auth User user, @Valid Message message) {

        List<PhoneNumber> phoneNumbers = phoneNumberDAO
                .findByAccountIdAndNumber(user.getAccountId(), message.getFrom());

        if (phoneNumbers.isEmpty()) {
            SmsResponse smsResponse = new SmsResponse();
            smsResponse.setError(new ArrayList<String>() {{
                add("to parameter not found");
            }});
            return Response.status(Response.Status.BAD_REQUEST).entity(smsResponse).build();
        }
        if (isStopMessage(message.getText())) {
            stopMessageCache.put(message.getFrom(), message.getTo());
        }

        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setMessage("inbound sms ok");

        return Response.ok(smsResponse).build();
    }


}

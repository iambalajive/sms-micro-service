package com.balaji.resources;

import com.balaji.cache.SmsMeterCache;
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

@Path("/outbound")
@Produces(MediaType.APPLICATION_JSON)
public class OutboundSmsResource {
    public OutboundSmsResource(PhoneNumberDAO phoneNumberDAO,
                               StopMessageCache stopMessageCache, SmsMeterCache smsMeterCache) {
        this.phoneNumberDAO = phoneNumberDAO;
        this.stopMessageCache = stopMessageCache;
        this.smsMeterCache = smsMeterCache;
    }

    private PhoneNumberDAO phoneNumberDAO;

    private StopMessageCache stopMessageCache;

    private SmsMeterCache smsMeterCache;


    @Path("/sms")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response outbound(@Auth User user, @Valid Message message) {

        List<PhoneNumber> phoneNumbers = phoneNumberDAO.
                findByAccountIdAndNumber(user.getAccountId(), message.getFrom());

        SmsResponse smsResponse = new SmsResponse();
        if(phoneNumbers.isEmpty()) {
            smsResponse.setError(new ArrayList<String>() {{
                add("from parameter not found");
            }});
            return Response.ok(smsResponse).build();
        }

        boolean isBlocked = stopMessageCache.doesPairExist(message.getFrom(),message.getTo());

        if(isBlocked) {
            smsResponse.setError(new ArrayList<String>() {{
                add("sms from " + message.getFrom()+ " to " + message.getTo() + " blocked by STOP request");
            }});
            return Response
                    .ok(smsResponse)
                    .build();
        }

       Integer count =  smsMeterCache.get(message.getFrom());

        if(count!=null && count >=50) {
            smsResponse.setError(new ArrayList<String>() {{
                add("limit reached for from "+message.getFrom());
            }});
            return Response
                    .ok(smsResponse)
                    .build();
        }

        smsResponse.setMessage("outbound sms ok");
        return Response.ok(message).build();
    }
}

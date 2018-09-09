package com.balaji.interceptors.response;

import com.balaji.resources.models.SmsResponse;
import io.dropwizard.jersey.validation.JerseyViolationException;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ValidationErrorHandler implements ExceptionMapper<JerseyViolationException> {
    @Override
    public Response toResponse(JerseyViolationException exception) {
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        List<String> messages = new ArrayList<>();

        Iterator<ConstraintViolation<?>> iterator = violations.iterator();

        while (iterator.hasNext()) {
            messages.add(iterator.next().getMessage());
        }

        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setError(messages);

        return Response.status(Response.Status.BAD_REQUEST).entity(smsResponse).build();
    }
}

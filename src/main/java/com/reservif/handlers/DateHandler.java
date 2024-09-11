package com.reservif.handlers;

import com.reservif.exceptions.DateException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DateHandler implements ExceptionMapper<DateException> {

    @Override
    public Response toResponse(DateException e) {

        var error = new ErrorMessage("Data nula", 400, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

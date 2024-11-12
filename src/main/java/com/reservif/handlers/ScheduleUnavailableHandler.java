package com.reservif.handlers;

import com.reservif.exceptions.ScheduleUnavailableException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ScheduleUnavailableHandler implements ExceptionMapper<ScheduleUnavailableException> {
    @Override
    public Response toResponse(ScheduleUnavailableException e) {
        var error = new ErrorMessage("Horário indisponível", 400, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

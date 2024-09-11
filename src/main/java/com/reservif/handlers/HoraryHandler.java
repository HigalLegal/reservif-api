package com.reservif.handlers;

import com.reservif.exceptions.HoraryException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class HoraryHandler implements ExceptionMapper<HoraryException> {

    @Override
    public Response toResponse(HoraryException e) {

        var error = new ErrorMessage("Horário nulo", 400, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

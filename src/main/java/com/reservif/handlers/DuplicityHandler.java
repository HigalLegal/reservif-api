package com.reservif.handlers;

import com.reservif.exceptions.DuplicityException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicityHandler implements ExceptionMapper<DuplicityException> {
    @Override
    public Response toResponse(DuplicityException e) {
        var error = new ErrorMessage("Valor duplicado", 400, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

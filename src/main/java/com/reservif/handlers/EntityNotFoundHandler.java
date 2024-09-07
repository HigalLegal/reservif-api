package com.reservif.handlers;

import com.reservif.handlers.entities.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class EntityNotFoundHandler implements ExceptionMapper<EntityNotFoundException> {

    @Override
    public Response toResponse(EntityNotFoundException e) {

        var error = new ErrorMessage("Recurso não encontrado", 404, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

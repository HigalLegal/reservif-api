package com.reservif.handlers;

import com.reservif.exceptions.PermissionException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PermissionHandler implements ExceptionMapper<PermissionException> {
    @Override
    public Response toResponse(PermissionException e) {

        var error = new ErrorMessage("Sem permissão", 401, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

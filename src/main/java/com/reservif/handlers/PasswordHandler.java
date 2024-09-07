package com.reservif.handlers;

import com.reservif.exceptions.PasswordException;
import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class PasswordHandler implements ExceptionMapper<PasswordException> {

    @Override
    public Response toResponse(PasswordException e) {

        var error = new ErrorMessage("Senha errada", 401, e.getMessage());

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

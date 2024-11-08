package com.reservif.handlers;

import com.reservif.handlers.entities.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.format.DateTimeParseException;

@Provider
public class DateTimeParseHandler implements ExceptionMapper<DateTimeParseException> {
    @Override
    public Response toResponse(DateTimeParseException e) {
        var error = new ErrorMessage();
        error.setTitle("Formato de data inválido.");
        error.setMessage("Formato da data deve ser no padrão brasileiro (dia/mês/ano) com o ano completo.");
        error.setStatusCode(400);

        return Response
                .status(error.getStatusCode())
                .entity(error)
                .build();
    }
}

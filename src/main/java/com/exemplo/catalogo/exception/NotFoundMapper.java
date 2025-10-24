package com.exemplo.catalogo.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException e) {
        ProblemDetails body = ProblemDetails.of(Response.Status.NOT_FOUND.getStatusCode(),
                "Not Found", e.getMessage(), null);
        return Response.status(Response.Status.NOT_FOUND)
                .type("application/problem+json")
                .entity(body)
                .build();
    }
}

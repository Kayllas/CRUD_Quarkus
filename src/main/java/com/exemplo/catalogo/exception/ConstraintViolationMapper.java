package com.exemplo.catalogo.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        String detail = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath()+": "+v.getMessage())
                .reduce((a,b) -> a+"; "+b).orElse("Validation error");
        ProblemDetails body = ProblemDetails.of(Response.Status.BAD_REQUEST.getStatusCode(),
                "Validation failed", detail, null);
        return Response.status(Response.Status.BAD_REQUEST)
                .type("application/problem+json")
                .entity(body)
                .build();
    }
}

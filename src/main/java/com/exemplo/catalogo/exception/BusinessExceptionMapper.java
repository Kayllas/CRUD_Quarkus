package com.exemplo.catalogo.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException e) {
        ProblemDetails body = ProblemDetails.of(422, // 422 Unprocessable Entity
                "Business rule violated", e.getMessage(), null);
        return Response.status(422) // evita depender de enum ausente
                .type("application/problem+json")
                .entity(body)
                .build();
    }
}

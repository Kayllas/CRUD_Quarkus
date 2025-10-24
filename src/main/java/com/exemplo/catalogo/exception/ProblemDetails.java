package com.exemplo.catalogo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.net.URI;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetails {
    public URI type;
    public String title;
    public int status;
    public String detail;
    public String instance;
    public OffsetDateTime timestamp = OffsetDateTime.now();

    public static ProblemDetails of(int status, String title, String detail, String instance) {
        ProblemDetails p = new ProblemDetails();
        p.status = status; p.title = title; p.detail = detail; p.instance = instance; return p;
    }
}

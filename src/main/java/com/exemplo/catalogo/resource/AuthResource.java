package com.exemplo.catalogo.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
public class AuthResource {
    @GET
    @Path("/token")
    public String token(@QueryParam("user") @DefaultValue("dev") String user,
                        @QueryParam("roles") @DefaultValue("ADMIN") String rolesCsv) {
        var roles = Set.of(rolesCsv.split(","));
        return Jwt.issuer("http://localhost/issuer")
                .subject(user)
                .groups(roles)
                .sign();
    }
}

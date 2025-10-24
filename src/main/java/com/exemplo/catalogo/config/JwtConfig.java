package com.exemplo.catalogo.config;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@DeclareRoles({"ADMIN","EDITOR"})
@ApplicationPath("/")
public class JwtConfig extends Application {
}

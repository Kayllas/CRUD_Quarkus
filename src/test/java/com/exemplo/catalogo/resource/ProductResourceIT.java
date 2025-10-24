package com.exemplo.catalogo.resource;

import com.exemplo.catalogo.resource.util.PostgresResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
public class ProductResourceIT {

    @Test
    void listagemBasica() {
        when().get("/api/produtos?page=0&size=10").then().statusCode(200)
                .body("page", is(0));
    }
}

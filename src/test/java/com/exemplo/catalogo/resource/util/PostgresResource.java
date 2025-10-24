package com.exemplo.catalogo.resource.util;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import java.util.Map;

public class PostgresResource implements QuarkusTestResourceLifecycleManager {
    private PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:15.3");

    @Override
    public Map<String, String> start() {
        db.start();
        return Map.of(
                "quarkus.datasource.jdbc.url", db.getJdbcUrl(),
                "quarkus.datasource.username", db.getUsername(),
                "quarkus.datasource.password", db.getPassword()
        );
    }

    @Override
    public void stop() { db.stop(); }
}

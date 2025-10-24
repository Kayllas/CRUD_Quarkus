# Catalogo (Quarkus CRUD)

CRUD de Produtos e Categorias com Quarkus, Panache, PostgreSQL, Flyway, OpenAPI, Health, Métricas e JWT opcional.

## Como rodar (Docker)

```bash
mvn -DskipTests package
docker compose up -d --build
# Acesse Swagger: http://localhost:8080/q/swagger-ui
# Health: http://localhost:8080/q/health
```

### Gerar token (apenas DEV) 
```bash
curl 'http://localhost:8080/auth/token?user=admin&roles=ADMIN,EDITOR'
```

Use o token com `Authorization: Bearer <TOKEN>` para POST/PUT/DELETE.

## Dev local
```bash
docker compose up -d db
mvn quarkus:dev
```

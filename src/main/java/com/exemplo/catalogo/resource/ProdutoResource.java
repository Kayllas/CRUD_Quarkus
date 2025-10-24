package com.exemplo.catalogo.resource;

import com.exemplo.catalogo.dto.PageResult;
import com.exemplo.catalogo.dto.ProdutoDTO;
import com.exemplo.catalogo.service.ProdutoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;

@Path("/api/produtos")
@Tag(name = "Produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject ProdutoService service;

    @GET
    @Operation(summary = "Listar produtos com paginação, ordenação e filtros")
    public PageResult<ProdutoDTO> list(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("20") int size,
                                       @QueryParam("sort") String sort,
                                       @QueryParam("filter") String filter,
                                       @QueryParam("categoriaId") Long categoriaId,
                                       @QueryParam("ativo") Boolean ativo,
                                       @QueryParam("precoMin") BigDecimal precoMin,
                                       @QueryParam("precoMax") BigDecimal precoMax) {
        return service.list(filter, categoriaId, ativo, precoMin, precoMax, page, size, sort);
    }

    @GET @Path("/{id}")
    @Operation(summary = "Buscar produto por id")
    public ProdutoDTO get(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Operation(summary = "Criar produto")
    @RolesAllowed({"ADMIN","EDITOR"})
    public Response create(@Valid ProdutoDTO dto, @Context UriInfo uri) {
        ProdutoDTO saved = service.create(dto);
        return Response.created(uri.getAbsolutePathBuilder().path(saved.id.toString()).build()).entity(saved).build();
    }

    @PUT @Path("/{id}")
    @Operation(summary = "Atualizar produto")
    @RolesAllowed({"ADMIN","EDITOR"})
    public ProdutoDTO update(@PathParam("id") Long id, @Valid ProdutoDTO dto) {
        return service.update(id, dto);
    }

    @DELETE @Path("/{id}")
    @Operation(summary = "Remover produto")
    @RolesAllowed({"ADMIN"})
    public void delete(@PathParam("id") Long id) { service.delete(id); }
}

package com.exemplo.catalogo.resource;

import com.exemplo.catalogo.dto.CategoriaDTO;
import com.exemplo.catalogo.service.CategoriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/categorias")
@Tag(name = "Categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {
    @Inject CategoriaService service;

    @GET public List<CategoriaDTO> list() { return service.findAll(); }
    @GET @Path("/{id}") public CategoriaDTO get(@PathParam("id") Long id) { return service.findById(id); }

    @POST @RolesAllowed({"ADMIN","EDITOR"})
    public Response create(@Valid CategoriaDTO dto, @Context UriInfo uri) {
        var saved = service.create(dto);
        return Response.created(uri.getAbsolutePathBuilder().path(saved.id.toString()).build()).entity(saved).build();
    }

    @PUT @Path("/{id}") @RolesAllowed({"ADMIN","EDITOR"})
    public CategoriaDTO update(@PathParam("id") Long id, @Valid CategoriaDTO dto) { return service.update(id, dto); }

    @DELETE @Path("/{id}") @RolesAllowed({"ADMIN"})
    public void delete(@PathParam("id") Long id) { service.delete(id); }
}

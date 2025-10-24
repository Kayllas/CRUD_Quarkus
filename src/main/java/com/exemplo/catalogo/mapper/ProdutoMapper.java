package com.exemplo.catalogo.mapper;

import com.exemplo.catalogo.domain.Categoria;
import com.exemplo.catalogo.domain.Produto;
import com.exemplo.catalogo.dto.ProdutoDTO;
import org.mapstruct.*;

@Mapper(componentModel = "cdi")
public interface ProdutoMapper {
    @Mapping(target = "categoriaId", source = "categoria.id")
    ProdutoDTO toDTO(Produto entity);

    @Mapping(target = "categoria", expression = "java(toCategoria(dto.categoriaId))")
    Produto toEntity(ProdutoDTO dto);

    default Categoria toCategoria(Long id) {
        if (id == null) return null;
        Categoria c = new Categoria();
        c.id = id;
        return c;
    }
}

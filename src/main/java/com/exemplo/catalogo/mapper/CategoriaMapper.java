package com.exemplo.catalogo.mapper;

import com.exemplo.catalogo.domain.Categoria;
import com.exemplo.catalogo.dto.CategoriaDTO;
import org.mapstruct.*;

@Mapper(componentModel = "cdi")
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria entity);
    Categoria toEntity(CategoriaDTO dto);
}

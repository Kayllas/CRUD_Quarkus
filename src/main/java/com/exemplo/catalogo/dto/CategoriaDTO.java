package com.exemplo.catalogo.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {
    public Long id;
    @NotBlank
    public String nome;
}

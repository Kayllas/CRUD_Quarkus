package com.exemplo.catalogo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProdutoDTO {
    public Long id;
    @NotBlank
    public String nome;
    @NotNull @DecimalMin("0.0")
    public BigDecimal preco;
    @NotNull
    public Boolean ativo;
    @NotNull
    public Long categoriaId;
}

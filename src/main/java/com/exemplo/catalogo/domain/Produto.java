package com.exemplo.catalogo.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos",
       uniqueConstraints = @UniqueConstraint(name = "uk_produto_nome_categoria", columnNames = {"nome", "categoria_id"}))
public class Produto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Column(nullable = false, length = 160)
    public String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(nullable = false, precision = 15, scale = 2)
    public BigDecimal preco;

    @NotNull
    @Column(nullable = false)
    public Boolean ativo = Boolean.TRUE;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria"))
    public Categoria categoria;
}

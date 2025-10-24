package com.exemplo.catalogo.repository;

import com.exemplo.catalogo.domain.Produto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort; // <- pacote correto
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public PanacheQuery<Produto> queryByFilters(
            String nameLike,
            Long categoriaId,
            Boolean ativo,
            BigDecimal precoMin,
            BigDecimal precoMax,
            String sortField,
            boolean asc) {

        StringBuilder jpql = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (nameLike != null && !nameLike.isBlank()) {
            jpql.append(" and lower(nome) like :nome");
            params.put("nome", "%" + nameLike.toLowerCase() + "%");
        }
        if (categoriaId != null) { jpql.append(" and categoria.id = :cid"); params.put("cid", categoriaId); }
        if (ativo != null)       { jpql.append(" and ativo = :ativo");     params.put("ativo", ativo); }
        if (precoMin != null)    { jpql.append(" and preco >= :pmin");      params.put("pmin", precoMin); }
        if (precoMax != null)    { jpql.append(" and preco <= :pmax");      params.put("pmax", precoMax); }

        Sort sort = null;
        if (sortField != null && !sortField.isBlank()) {
            sort = asc ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        }

        // Panache não aceita Sort nulo nesse overload
        return (sort != null)
                ? find(jpql.toString(), sort, params)
                : find(jpql.toString(), params);
    }
}

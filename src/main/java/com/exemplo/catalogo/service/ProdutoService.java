package com.exemplo.catalogo.service;

import com.exemplo.catalogo.domain.Categoria;
import com.exemplo.catalogo.domain.Produto;
import com.exemplo.catalogo.dto.PageResult;
import com.exemplo.catalogo.dto.ProdutoDTO;
import com.exemplo.catalogo.exception.BusinessException;
import com.exemplo.catalogo.mapper.ProdutoMapper;
import com.exemplo.catalogo.repository.CategoriaRepository;
import com.exemplo.catalogo.repository.ProdutoRepository;
import com.exemplo.catalogo.service.gateway.OrderGateway;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProdutoService {
    @Inject public ProdutoRepository repo;
    @Inject public CategoriaRepository categoriaRepo;
    @Inject public ProdutoMapper mapper;
    @Inject public OrderGateway orderGateway;

    public PageResult<ProdutoDTO> list(String filter, Long categoriaId, Boolean ativo,
                                       BigDecimal precoMin, BigDecimal precoMax,
                                       int page, int size, String sort) {
        String sortField = null; boolean asc = true;
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            sortField = parts[0];
            if (parts.length > 1) asc = parts[1].equalsIgnoreCase("asc");
        }
        PanacheQuery<Produto> q = repo.queryByFilters(filter, categoriaId, ativo, precoMin, precoMax, sortField, asc);
        q.page(page, size);
        List<ProdutoDTO> content = q.list().stream().map(mapper::toDTO).toList();
        return new PageResult<>(content, page, size, q.count());
    }

    public ProdutoDTO findById(Long id) {
        Produto p = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        return mapper.toDTO(p);
    }

    @Transactional
    public ProdutoDTO create(ProdutoDTO dto) {
        Categoria cat = categoriaRepo.findByIdOptional(dto.categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria inexistente"));
        Produto p = mapper.toEntity(dto);
        p.categoria = cat;
        repo.persist(p);
        return mapper.toDTO(p);
    }

    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto p = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        boolean vaiDesativar = Boolean.FALSE.equals(dto.ativo) && Boolean.TRUE.equals(p.ativo);
        if (vaiDesativar && orderGateway.hasOpenOrders(id)) {
            throw new BusinessException("Produto com pedidos abertos não pode ser desativado");
        }
        p.nome = dto.nome;
        p.preco = dto.preco;
        p.ativo = dto.ativo;
        if (!p.categoria.id.equals(dto.categoriaId)) {
            p.categoria = categoriaRepo.findByIdOptional(dto.categoriaId)
                    .orElseThrow(() -> new NotFoundException("Categoria inexistente"));
        }
        return mapper.toDTO(p);
    }

    @Transactional
    public void delete(Long id) {
        Produto p = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        if (orderGateway.hasOpenOrders(id)) {
            throw new BusinessException("Produto com pedidos abertos não pode ser removido");
        }
        repo.delete(p);
    }
}

package com.exemplo.catalogo.service;

import com.exemplo.catalogo.domain.Categoria;
import com.exemplo.catalogo.dto.CategoriaDTO;
import com.exemplo.catalogo.exception.BusinessException;
import com.exemplo.catalogo.mapper.CategoriaMapper;
import com.exemplo.catalogo.repository.CategoriaRepository;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CategoriaService {
    @Inject CategoriaRepository repo;
    @Inject CategoriaMapper mapper;

    public List<CategoriaDTO> findAll() {
        return repo.listAll().stream().map(mapper::toDTO).toList();
    }

    public CategoriaDTO findById(Long id) {
        Categoria c = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return mapper.toDTO(c);
    }

    @Transactional
    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria c = mapper.toEntity(dto);
        repo.persist(c);
        return mapper.toDTO(c);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria c = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        c.nome = dto.nome;
        return mapper.toDTO(c);
    }

    @Transactional
    public void delete(Long id) {
        Categoria c = repo.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        long qtd = Panache.getEntityManager()
                .createQuery("select count(p) from Produto p where p.categoria.id = :id", Long.class)
                .setParameter("id", id).getSingleResult();
        if (qtd > 0) throw new BusinessException("Categoria possui produtos vinculados");
        repo.delete(c);
    }
}

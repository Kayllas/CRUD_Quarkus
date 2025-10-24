package com.exemplo.catalogo.service;

import com.exemplo.catalogo.domain.Categoria;
import com.exemplo.catalogo.domain.Produto;
import com.exemplo.catalogo.dto.ProdutoDTO;
import com.exemplo.catalogo.exception.BusinessException;
import com.exemplo.catalogo.mapper.ProdutoMapper;
import com.exemplo.catalogo.repository.CategoriaRepository;
import com.exemplo.catalogo.repository.ProdutoRepository;
import com.exemplo.catalogo.service.gateway.OrderGateway;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

public class ProdutoServiceTest {
    ProdutoRepository repo = mock(ProdutoRepository.class);
    CategoriaRepository categoriaRepo = mock(CategoriaRepository.class);
    OrderGateway orderGateway = mock(OrderGateway.class);
    ProdutoMapper mapper = new com.exemplo.catalogo.mapper.ProdutoMapperImpl();

    ProdutoService service;

    @BeforeEach
    void setup() {
        service = new ProdutoService();
        service.repo = repo; service.categoriaRepo = categoriaRepo; service.mapper = mapper; service.orderGateway = orderGateway;
    }

    @Test
    void naoDeveDesativarComPedidosAbertos() {
        Produto p = new Produto(); p.id = 1L; p.nome = "X"; p.preco = new BigDecimal("10"); p.ativo = true; p.categoria = new Categoria(); p.categoria.id = 5L;
        when(repo.findByIdOptional(1L)).thenReturn(Optional.of(p));
        when(orderGateway.hasOpenOrders(1L)).thenReturn(true);

        ProdutoDTO dto = new ProdutoDTO(); dto.id = 1L; dto.nome = "X"; dto.preco = new BigDecimal("10"); dto.ativo = false; dto.categoriaId = 5L;
        assertThrows(BusinessException.class, () -> service.update(1L, dto));
    }
}

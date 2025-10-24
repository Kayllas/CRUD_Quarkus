package com.exemplo.catalogo.service.gateway;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderGateway {
    public boolean hasOpenOrders(Long produtoId) {
        // Simulação: em produção consultar serviço de Pedidos
        return false;
    }
}

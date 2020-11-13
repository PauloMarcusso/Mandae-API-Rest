package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.cancelar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.entregue();
    }
}

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

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para $s", pedidoId,
                            pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao())
            );
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {

        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedidoId, pedido.getStatus().getDescricao(), StatusPedido.CRIADO.getDescricao())
            );
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(String.format(
                    "Status do pedido %d não pode ser alterado de %s para %s",
                    pedidoId, pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao()
            ));
        }

        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}

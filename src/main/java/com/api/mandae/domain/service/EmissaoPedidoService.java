package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.PedidoNaoEncontradoException;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() ->
                new PedidoNaoEncontradoException(
                        String.format("Pedido com código %d não encontrado", pedidoId)));

    }
}

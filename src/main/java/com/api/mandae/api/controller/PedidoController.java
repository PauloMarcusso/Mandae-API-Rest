package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.pedido.PedidoConverter;
import com.api.mandae.api.assembler.pedido.PedidoResumoConverter;
import com.api.mandae.api.model.PedidoDTO;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.repository.PedidoRepository;
import com.api.mandae.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoConverter pedidoConverter;

    @Autowired
    private PedidoResumoConverter pedidoResumoConverter;

    @Autowired
    private EmissaoPedidoService cadastroPedido;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        return pedidoResumoConverter.toCollectionDTO(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId){
        Pedido pedido = cadastroPedido.buscarOuFalhar(pedidoId);
        return pedidoConverter.toDTO(pedido);
    }
}

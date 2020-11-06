package com.api.mandae.api.assembler.pedido;

import com.api.mandae.api.model.PedidoDTO;
import com.api.mandae.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoConverter{

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO toDTO(Pedido pedido){
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> toCollectionDTO(List<Pedido> pedidos){
        return pedidos.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
    }
}

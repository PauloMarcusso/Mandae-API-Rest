package com.api.mandae.api.assembler.pedido;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.api.model.input.PedidoInput;
import com.api.mandae.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoConverter {

    @Autowired
    private ModelMapper modelMapper;


    public PedidoResumoDTO toDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }


    public List<PedidoResumoDTO> toCollectionDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> toDTO(pedido)).collect(Collectors.toList());
    }


}

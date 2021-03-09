package com.api.mandae.api.assembler.pedido;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.PedidoController;
import com.api.mandae.api.controller.RestauranteController;
import com.api.mandae.api.controller.UsuarioController;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    public PedidoResumoConverter(){
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO pedidoDTO = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(mandaeLinks.linkToPedidos("pedidos"));

        pedidoDTO.getRestaurante().add(
                mandaeLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDTO.getCliente().add(mandaeLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoDTO;
    }


    public List<PedidoResumoDTO> toCollectionDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
    }

}

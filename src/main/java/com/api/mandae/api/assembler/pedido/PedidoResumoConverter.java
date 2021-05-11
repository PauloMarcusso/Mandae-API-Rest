package com.api.mandae.api.assembler.pedido;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.PedidoController;
import com.api.mandae.api.model.PedidoResumoDTO;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity algaSecurity;

    public PedidoResumoConverter() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO pedidoDTO = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        if (algaSecurity.podePesquisarPedidos()) {
            pedidoDTO.add(mandaeLinks.linkToPedidos("pedidos"));
        }

        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoDTO.getRestaurante().add(
                    mandaeLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoDTO.getCliente().add(mandaeLinks.linkToUsuario(pedido.getCliente().getId()));
        }

        return pedidoDTO;
    }


    public List<PedidoResumoDTO> toCollectionDTO(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
    }

}

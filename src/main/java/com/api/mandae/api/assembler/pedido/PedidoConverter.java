package com.api.mandae.api.assembler.pedido;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.PedidoController;
import com.api.mandae.api.model.PedidoDTO;
import com.api.mandae.api.model.input.PedidoInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public PedidoConverter() {
        super(PedidoController.class, PedidoDTO.class);
    }

    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDTO);

        pedidoDTO.add(mandaeLinks.linkToPedidos("pedidos"));

        if(mandaeSecurity.podeGerenciarPedidos(pedido.getCodigo())){
            if (pedido.podeSerConfirmado()) {
                pedidoDTO.add(mandaeLinks.linkToConfirmarPedido(pedido.getCodigo(), "confirmar"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoDTO.add(mandaeLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoDTO.add(mandaeLinks.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
            }
        }


        pedidoDTO.getRestaurante().add(
                mandaeLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoDTO.getCliente().add(
                mandaeLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoDTO.getFormaPagamento().add(
                mandaeLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoDTO.getEnderecoEntrega().getCidade().add(
                mandaeLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoDTO.getItens().forEach(item -> {
            item.add(mandaeLinks.linkToProduto(
                    pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        return pedidoDTO;
    }

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }


}

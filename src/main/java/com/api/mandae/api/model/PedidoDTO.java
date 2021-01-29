package com.api.mandae.api.model;

import com.api.mandae.domain.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoDTO extends RepresentationModel<PedidoDTO> {


    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2019-12-01T20:35:10Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2019-12-01T20:55:30Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime dataCancelamento;

    private FormaPagamentoDTO formaPagamento;

    private RestauranteResumoDTO restaurante;

    private UsuarioDTO cliente;

    private EnderecoDTO enderecoEntrega;

    private List<ItemPedidoDTO> itens = new ArrayList<>();
}

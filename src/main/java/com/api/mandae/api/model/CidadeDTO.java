package com.api.mandae.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Guarulhos")
    private String nome;

    private EstadoDTO estado;

}

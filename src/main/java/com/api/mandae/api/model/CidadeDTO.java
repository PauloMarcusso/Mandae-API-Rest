package com.api.mandae.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Guarulhos")
    private String nome;

    private EstadoIdInput estado;

}

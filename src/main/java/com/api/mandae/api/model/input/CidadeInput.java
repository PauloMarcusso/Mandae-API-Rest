package com.api.mandae.api.model.input;

import com.api.mandae.api.model.EstadoIdInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @ApiModelProperty(example = "Guarulhos")
    @NotBlank
    private String nome;

    @NotNull
    private EstadoIdInput estado;
}

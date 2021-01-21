package com.api.mandae.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoInput {

    @ApiModelProperty(example = "Cartão de crédito", required = true)
    @NotBlank
    private String descricao;
}

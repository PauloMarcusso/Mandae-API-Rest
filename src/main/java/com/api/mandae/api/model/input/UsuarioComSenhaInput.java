package com.api.mandae.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaInput extends UsuarioInput{

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senha;
}

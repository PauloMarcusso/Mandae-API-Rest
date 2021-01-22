package com.api.mandae.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {

    @   ApiModelProperty(example = "João da Silva", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "joao@mandae.com.br", required = true)
    @Email
    @NotBlank
    private String email;
}

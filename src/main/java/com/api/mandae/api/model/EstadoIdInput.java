package com.api.mandae.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoIdInput {

    @ApiModelProperty(example = "1")
    @NotBlank
    private Long id;
}

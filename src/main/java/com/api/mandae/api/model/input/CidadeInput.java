package com.api.mandae.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.api.mandae.api.model.EstadoIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@NotBlank
	private String nome;
	
	@NotNull
	private EstadoIdInput estado;
}

package com.api.mandae.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {

	@NotBlank
	private Long id;
}

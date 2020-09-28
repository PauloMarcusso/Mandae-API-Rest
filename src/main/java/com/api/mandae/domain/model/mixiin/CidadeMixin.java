package com.api.mandae.domain.model.mixin;

import com.api.mandae.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	
}

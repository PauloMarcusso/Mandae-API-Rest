package com.api.mandae.domain.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.api.mandae.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CozinhaMixin {

	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();
}

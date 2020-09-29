package com.api.mandae.api.assembler;

import org.springframework.stereotype.Component;

import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {

		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(cozinha);

		return restaurante;
	}
}

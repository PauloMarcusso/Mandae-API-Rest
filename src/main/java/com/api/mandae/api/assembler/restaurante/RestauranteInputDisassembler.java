package com.api.mandae.api.assembler.restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {

		return modelMapper.map(restauranteInput, Restaurante.class);

//		Restaurante restaurante = new Restaurante();
//		restaurante.setNome(restauranteInput.getNome());
//		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//
//		Cozinha cozinha = new Cozinha();
//		cozinha.setId(restauranteInput.getCozinha().getId());
//		restaurante.setCozinha(cozinha);
//
//		return restaurante;
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		//Para evitar lançar a exception org.springframework.orm.jpa.JpaSystemException:
		//identifier of an instance of com.api.mandae.domain.model.Cozinha was altered from 1 to 4
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInput, restaurante);
	}
}

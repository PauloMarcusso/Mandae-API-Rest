package com.api.mandae.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.api.model.RestauranteDTO;
import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;

@Component
public class RestauranteConverter implements Converter<Restaurante, RestauranteDTO, RestauranteInput>{

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public RestauranteDTO toDTO(Restaurante restaurante) {
		
		return modelMapper.map(restaurante, RestauranteDTO.class);
		
//		CozinhaDTO cozinhaDTO = new CozinhaDTO();
//		cozinhaDTO.setId(restaurante.getCozinha().getId());
//		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//
//		RestauranteDTO restauranteDTO = new RestauranteDTO();
//		restauranteDTO.setId(restaurante.getId());
//		restauranteDTO.setNome(restaurante.getNome());
//		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//		restauranteDTO.setCozinha(cozinhaDTO);
//		return restauranteDTO;
	}

	@Override
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream().map(restaurante -> toDTO(restaurante)).collect(Collectors.toList());
	}

	@Override
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
	
	@Override
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInput, restaurante);
	}

	

}

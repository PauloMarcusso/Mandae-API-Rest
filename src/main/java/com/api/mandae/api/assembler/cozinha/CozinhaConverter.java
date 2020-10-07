package com.api.mandae.api.assembler.cozinha;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.CozinhaDTO;
import com.api.mandae.api.model.input.CozinhaInput;
import com.api.mandae.domain.model.Cozinha;

@Component
public class CozinhaConverter implements Converter<Cozinha, CozinhaDTO, CozinhaInput>{
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CozinhaDTO toDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}

	@Override
	public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream().map(cozinha -> toDTO(cozinha)).collect(Collectors.toList());
	}

	@Override
	public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return modelMapper.map(cozinhaInput, Cozinha.class);
	}

	@Override
	public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		modelMapper.map(cozinhaInput, cozinha);
	}

}

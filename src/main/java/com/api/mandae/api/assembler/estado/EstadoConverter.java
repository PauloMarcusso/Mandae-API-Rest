package com.api.mandae.api.assembler.estado;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.EstadoDTO;
import com.api.mandae.api.model.input.EstadoInput;
import com.api.mandae.domain.model.Estado;

@Component
public class EstadoConverter implements Converter<Estado, EstadoDTO, EstadoInput>{
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EstadoDTO toDTO(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}

	@Override
	public List<EstadoDTO> toCollectionDTO(List<Estado> estados) {
		return estados.stream().map(estado -> toDTO(estado)).collect(Collectors.toList());
	}

	@Override
	public Estado toDomainObject(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}

	@Override
	public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}

	
}

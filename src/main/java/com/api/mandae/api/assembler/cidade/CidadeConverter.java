package com.api.mandae.api.assembler.cidade;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Estado;

@Component
public class CidadeConverter implements Converter<Cidade, CidadeDTO, CidadeInput>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CidadeDTO toDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}

	@Override
	public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades) {
		return cidades.stream().map(cidade -> toDTO(cidade)).collect(Collectors.toList());
	}

	@Override
	public Cidade toDomainObject(CidadeInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}

	@Override
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeInput, cidade);
	}

}

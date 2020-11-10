package com.api.mandae.core.modelmapper;

import com.api.mandae.api.model.EnderecoDTO;
import com.api.mandae.api.model.input.ItemPedidoInput;
import com.api.mandae.domain.model.Endereco;
import com.api.mandae.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		var enderecoToEnderecoDTOTypeMap =
				modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

		enderecoToEnderecoDTOTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));


		return modelMapper;
	}
}

package com.api.mandae.api.assembler.produto;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.ProdutoDTO;
import com.api.mandae.api.model.input.ProdutoInput;
import com.api.mandae.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoConverter implements Converter<Produto, ProdutoDTO, ProdutoInput> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos) {
        return produtos.stream().map(produto -> toDTO(produto)).collect(Collectors.toList());
    }

    @Override
    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    @Override
    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}

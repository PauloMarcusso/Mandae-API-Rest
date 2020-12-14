package com.api.mandae.api.assembler.fotoproduto;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.model.FotoProdutoDTO;
import com.api.mandae.api.model.input.FormaPagamentoInput;
import com.api.mandae.api.model.input.FotoProdutoInput;
import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoConverter{

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto foto){
        return modelMapper.map(foto, FotoProdutoDTO.class);
    }
}

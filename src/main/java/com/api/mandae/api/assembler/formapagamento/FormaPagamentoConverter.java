package com.api.mandae.api.assembler.formapagamento;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.model.input.FormaPagamentoInput;
import com.api.mandae.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoConverter implements Converter<FormaPagamento, FormaPagamentoDTO, FormaPagamentoInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    @Override
    public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> list) {
        return null;
    }

    public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamento) {
        return formasPagamento.stream().map(formaPagamento -> toDTO(formaPagamento)).collect(Collectors.toList());
    }

    @Override
    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    @Override
    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }

}

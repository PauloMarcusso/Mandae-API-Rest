package com.api.mandae.api.assembler.formapagamento;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.FormaPagamentoController;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.model.input.FormaPagamentoInput;
import com.api.mandae.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoConverter extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    public FormaPagamentoConverter() {
        super(FormaPagamentoController.class, FormaPagamentoDTO.class);
    }

    @Override
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {

        FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoDTO);

        formaPagamentoDTO.add(mandaeLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoDTO;
    }

    @Override
    public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities).add(mandaeLinks.linkToFormasPagamento());
    }


    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }

//    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
//        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
//    }
//
//    public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> formasPagamento) {
//        return formasPagamento.stream().map(formaPagamento -> toDTO(formaPagamento)).collect(Collectors.toList());
//    }
//
//
//
//

}

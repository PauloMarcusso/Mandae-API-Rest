package com.api.mandae.api.assembler.cozinha;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.CozinhaController;
import com.api.mandae.api.model.CozinhaDTO;
import com.api.mandae.api.model.input.CozinhaInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public CozinhaConverter() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {

        CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDTO);

        if (mandaeSecurity.podeConsultarCozinhas()) {
            cozinhaDTO.add(mandaeLinks.linkToCozinhas("cozinhas"));
        }

        return cozinhaDTO;
    }

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }


    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }

}

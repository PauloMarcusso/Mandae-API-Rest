package com.api.mandae.api.assembler.estado;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.EstadoController;
import com.api.mandae.api.model.EstadoDTO;
import com.api.mandae.api.model.input.EstadoInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoConverter extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public EstadoConverter() {
        super(EstadoController.class, EstadoDTO.class);
    }

    public EstadoDTO toModel(Estado estado) {

        EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoDTO);

        if (mandaeSecurity.podeConsultarEstados()) {
            estadoDTO.add(mandaeLinks.linkToEstados("estados"));
        }

        return estadoDTO;
    }

    @Override public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);

        if (mandaeSecurity.podeConsultarEstados()) {
            collectionModel.add(mandaeLinks.linkToEstados());
        }

        return collectionModel;
    }

    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }


    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }


}

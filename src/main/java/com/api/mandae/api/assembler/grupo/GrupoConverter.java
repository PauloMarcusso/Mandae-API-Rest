package com.api.mandae.api.assembler.grupo;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.GrupoController;
import com.api.mandae.api.model.GrupoDTO;
import com.api.mandae.api.model.input.GrupoInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoConverter extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public GrupoConverter() {
        super(GrupoController.class, GrupoDTO.class);
    }

    @Override
    public GrupoDTO toModel(Grupo grupo) {
        GrupoDTO grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        if (mandaeSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(mandaeLinks.linkToGrupos("grupos"));

            grupoModel.add(mandaeLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);

        if (mandaeSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(mandaeLinks.linkToGrupos());
        }

        return collectionModel;
    }


    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }


    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}

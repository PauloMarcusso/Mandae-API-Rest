package com.api.mandae.api.assembler.permissao;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.model.PermissaoDTO;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoConverter implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks algaLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    @Override
    public PermissaoDTO toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    @Override
    public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoDTO> collectionModel
                = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (mandaeSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToPermissoes());
        }

        return collectionModel;
    }
}

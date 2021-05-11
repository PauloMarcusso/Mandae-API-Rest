package com.api.mandae.api.assembler.usuario;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.controller.UsuarioController;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.model.input.UsuarioInput;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    public UsuarioConverter() {
        super(UsuarioController.class, UsuarioDTO.class);
    }


    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        if (mandaeSecurity.podeConsultarUsuariosGruposPermissoes()){
            usuarioDTO.add(mandaeLinks.linkToUsuarios("usuarios"));
            usuarioDTO.add(mandaeLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }

        return usuarioDTO;
    }

    @Override public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(mandaeLinks.linkToUsuarios());
    }

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}

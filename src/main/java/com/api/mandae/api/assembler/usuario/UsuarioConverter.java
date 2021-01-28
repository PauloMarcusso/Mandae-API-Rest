package com.api.mandae.api.assembler.usuario;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.controller.UsuarioController;
import com.api.mandae.api.controller.UsuarioGrupoController;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.model.input.UsuarioInput;
import com.api.mandae.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioConverter() {
        super(UsuarioController.class, UsuarioDTO.class);
    }


    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        usuarioDTO.add(linkTo(methodOn(UsuarioController.class).buscar(usuarioDTO.getId())).withSelfRel());

        usuarioDTO.add(linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioDTO.getId())).withSelfRel());

        return usuarioDTO;
    }

    @Override public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(linkTo(UsuarioController.class).withSelfRel());
    }

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}

package com.api.mandae.api.assembler.usuario;

import com.api.mandae.api.assembler.Converter;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.model.input.UsuarioInput;
import com.api.mandae.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioConverter implements Converter<Usuario, UsuarioDTO, UsuarioInput> {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> toCollectionDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
    }

    public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> usuarios) {
        return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
    }

    @Override
    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    @Override
    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }
}

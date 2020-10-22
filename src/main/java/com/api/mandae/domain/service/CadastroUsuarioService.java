package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.UsuarioNaoEncontradoException;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarOuFalhar(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException(id));
    }

    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}

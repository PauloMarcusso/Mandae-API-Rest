package com.api.mandae.domain.service;

import com.api.mandae.api.model.input.UsuarioInput;
import com.api.mandae.domain.exception.EntidadeEmUsoException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.UsuarioNaoEncontradoException;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.detach(usuario);

       Optional<Usuario> usuarioExistente =  usuarioRepository.findByEmail(usuario.getEmail());

       if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){
           throw new NegocioException(
                   String.format("Já existe um usuário cadastrado com o email %s", usuario.getEmail()));
       }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha){

        Usuario usuario = buscarOuFalhar(usuarioId);

        if(usuario.senhaNaoCoincideCom(senhaAtual)){
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void remover(Long id) {

        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Usuario de código %d não pode ser removido pois está em uso", id));
        }

    }
}

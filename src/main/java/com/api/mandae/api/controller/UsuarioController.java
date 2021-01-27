package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.usuario.UsuarioConverter;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.model.input.SenhaInput;
import com.api.mandae.api.model.input.UsuarioComSenhaInput;
import com.api.mandae.api.model.input.UsuarioInput;
import com.api.mandae.api.openapi.controller.UsuarioControllerOpenApi;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.UsuarioRepository;
import com.api.mandae.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioConverter usuarioConverter;


    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioConverter.toCollectionDTO(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable Long id) {
        return usuarioConverter.toModel(cadastroUsuario.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioConverter.toDomainObject(usuarioInput);
        return usuarioConverter.toModel(cadastroUsuario.salvar(usuario));
    }


    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {

        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(id);
        usuarioConverter.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioConverter.toModel(usuarioAtual);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuario.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }

    @DeleteMapping("{/id}")
    public void excluir(@PathVariable Long id) {
        cadastroUsuario.remover(id);
    }


}

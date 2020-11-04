package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.grupo.GrupoConverter;
import com.api.mandae.api.model.GrupoDTO;
import com.api.mandae.domain.model.Usuario;
import com.api.mandae.domain.repository.GrupoRepository;
import com.api.mandae.domain.service.CadastroGrupoService;
import com.api.mandae.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoConverter grupoConverter;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

        return grupoConverter.toCollectionDTO(usuario.getGrupos());

    }

    @PutMapping("/{grupoId}")
    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuario.associar(usuarioId, grupoId);

    }

    @DeleteMapping("/{grupoId}")
    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuario.desassociar(usuarioId, grupoId);
    }
}

package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.permissao.PermissaoConverter;
import com.api.mandae.api.model.PermissaoDTO;
import com.api.mandae.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.api.mandae.domain.model.Grupo;
import com.api.mandae.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private PermissaoConverter permissaoConverter;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {

        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoConverter.toCollectionDTO(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        cadastroGrupo.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
    }
}

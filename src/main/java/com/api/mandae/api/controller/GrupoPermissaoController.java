package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.permissao.PermissaoConverter;
import com.api.mandae.api.model.PermissaoDTO;
import com.api.mandae.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.api.mandae.domain.model.Grupo;
import com.api.mandae.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private PermissaoConverter permissaoConverter;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @GetMapping
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {

        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoConverter.toCollectionModel(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}

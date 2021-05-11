package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.assembler.permissao.PermissaoConverter;
import com.api.mandae.api.model.PermissaoDTO;
import com.api.mandae.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.core.security.MandaeSecurity;
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

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {

        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

         CollectionModel<PermissaoDTO> permissoesModel
                = permissaoConverter.toCollectionModel(grupo.getPermissoes())
                .removeLinks();

        permissoesModel.add(mandaeLinks.linkToGrupoPermissoes(grupoId));

        if (mandaeSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesModel.add(mandaeLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

            permissoesModel.getContent().forEach(permissaoModel -> {
                permissaoModel.add(mandaeLinks.linkToGrupoPermissaoDesassociacao(
                        grupoId, permissaoModel.getId(), "desassociar"));
            });
        }

        return permissoesModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupo.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}

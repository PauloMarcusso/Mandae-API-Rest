package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.assembler.usuario.UsuarioConverter;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.core.security.MandaeSecurity;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.service.CadastroRestauranteService;
import com.api.mandae.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    @Override
    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioDTO> usuariosModel = usuarioConverter
                .toCollectionModel(restaurante.getResponsaveis())
                .removeLinks();

        usuariosModel.add(mandaeLinks.linkToResponsaveisRestaurante(restauranteId));

        if (mandaeSecurity.podeGerenciarCadastroRestaurantes()) {
            usuariosModel.add(mandaeLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

            usuariosModel.getContent().stream().forEach(usuarioModel -> {
                usuarioModel.add(mandaeLinks.linkToRestauranteResponsavelDesassociacao(
                        restauranteId, usuarioModel.getId(), "desassociar"));
            });
        }

        return usuariosModel;
    }

    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarUsuarioResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarUsuarioResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}

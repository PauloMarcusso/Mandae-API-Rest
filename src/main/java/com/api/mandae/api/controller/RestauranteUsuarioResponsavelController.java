package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.usuario.UsuarioConverter;
import com.api.mandae.api.model.UsuarioDTO;
import com.api.mandae.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.service.CadastroRestauranteService;
import com.api.mandae.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioConverter usuarioConverter;

    @GetMapping
    public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return usuarioConverter.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withSelfRel());

    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarUsuarioResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarUsuarioResponsavel(restauranteId, usuarioId);
    }
}

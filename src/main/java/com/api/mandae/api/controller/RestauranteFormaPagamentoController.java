package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.assembler.formapagamento.FormaPagamentoConverter;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private MandaeLinks mandaeLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoDTO> formasPagamentoDTO =
                formaPagamentoConverter.toCollectionModel(restaurante.getFormasPagamento())
                        .removeLinks()
                        .add(mandaeLinks.linkToRestauranteFormasPagamento(restauranteId))
                        .add(mandaeLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
        formasPagamentoDTO.getContent().forEach(formaPagamentoDTO -> {
            formaPagamentoDTO.add(mandaeLinks.linkToRestauranteFormaPagamentoDesassociacao(
                    restauranteId, formaPagamentoDTO.getId(),"desassociar"));
        });
        return formasPagamentoDTO;

    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}

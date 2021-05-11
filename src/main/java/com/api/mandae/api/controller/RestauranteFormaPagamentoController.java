package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.api.assembler.formapagamento.FormaPagamentoConverter;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.core.security.MandaeSecurity;
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

    @Autowired
    private MandaeSecurity mandaeSecurity;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoDTO> formasPagamentoModel
                = formaPagamentoConverter.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks();

        formasPagamentoModel.add(mandaeLinks.linkToRestauranteFormasPagamento(restauranteId));

        if (mandaeSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
            formasPagamentoModel.add(mandaeLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

            formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
                formaPagamentoModel.add(mandaeLinks.linkToRestauranteFormaPagamentoDesassociacao(
                        restauranteId, formaPagamentoModel.getId(), "desassociar"));
            });
        }

        return formasPagamentoModel;

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}

package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
import com.api.mandae.core.security.MandaeSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private MandaeLinks mandaeLinks;

    @Autowired
    private MandaeSecurity mandaeSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (mandaeSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(mandaeLinks.linkToCozinhas("cozinhas"));
        }

        if (mandaeSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(mandaeLinks.linkToPedidos("pedidos"));
        }

        if (mandaeSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(mandaeLinks.linkToRestaurantes("restaurantes"));
        }

        if (mandaeSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(mandaeLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(mandaeLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(mandaeLinks.linkToPermissoes("permissoes"));
        }

        if (mandaeSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(mandaeLinks.linkToFormasPagamento("formas-pagamento"));
        }

        if (mandaeSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(mandaeLinks.linkToEstados("estados"));
        }

        if (mandaeSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(mandaeLinks.linkToCidades("cidades"));
        }

        if (mandaeSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(mandaeLinks.linkToEstatisticas("estatisticas"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}

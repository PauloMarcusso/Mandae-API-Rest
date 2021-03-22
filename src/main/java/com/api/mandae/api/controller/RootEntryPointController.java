package com.api.mandae.api.controller;

import com.api.mandae.api.MandaeLinks;
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

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(mandaeLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(mandaeLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(mandaeLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(mandaeLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(mandaeLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(mandaeLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(mandaeLinks.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(mandaeLinks.linkToEstados("estados"));
        rootEntryPointModel.add(mandaeLinks.linkToCidades("cidades"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}

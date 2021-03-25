package com.api.mandae.api.openapi.controller;

import com.api.mandae.api.model.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpeApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDTO> listar();
}

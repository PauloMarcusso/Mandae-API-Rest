package com.api.mandae.api.controller.openapi;

import com.api.mandae.api.exceptionhandler.Problem;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista todas as cidades")
    public List<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") Long id);

    @ApiOperation("Cadastra uma cidade")
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade") CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })

    public CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1") Long id,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados") CidadeInput cidadeInput);


    @ApiOperation("Deleta uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@ApiParam(value = "ID de uma cidade", example = "1") Long id);
}

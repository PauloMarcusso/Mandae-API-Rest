package com.api.mandae.api.openapi.controller;

import com.api.mandae.api.exceptionhandler.Problem;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.model.input.FormaPagamentoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api("Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public ResponseEntity<FormaPagamentoDTO> buscar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
                    Long formaPagamentoId, ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    public FormaPagamentoDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento")
                    FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public FormaPagamentoDTO atualizar(
            @ApiParam(value = "ID de uma forma de pagamento", example = "1")
                    Long formaPagamentoId,

            @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
                    FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public void excluir(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId);
}

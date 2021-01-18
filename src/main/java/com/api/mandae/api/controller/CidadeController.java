package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.cidade.CidadeConverter;
import com.api.mandae.api.exceptionhandler.Problem;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.domain.exception.EstadoNaoEncontradoException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.service.CadastroCidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeConverter cidadeConverter;

    @ApiOperation("Lista todas as cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeConverter.toCollectionDTO(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{id}")
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
        return cidadeConverter.toDTO(cadastroCidade.buscarOuFalhar(id));
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeConverter.toDomainObject(cidadeInput);

            return cidadeConverter.toDTO(cadastroCidade.salvar(cidade));

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{id}")
    public CidadeDTO atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados") @RequestBody @Valid CidadeInput cidadeInput) {

        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

//		BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeConverter.copyToDomainObject(cidadeInput, cidadeAtual);

        try {

            return cidadeConverter.toDTO(cadastroCidade.salvar(cidadeAtual));

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @ApiOperation("Deleta uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
        cadastroCidade.remover(id);
    }

}

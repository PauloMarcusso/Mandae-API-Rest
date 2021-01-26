package com.api.mandae.api.controller;

import com.api.mandae.api.ResourceUriHelper;
import com.api.mandae.api.assembler.cidade.CidadeConverter;
import com.api.mandae.api.openapi.controller.CidadeControllerOpenApi;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.domain.exception.EstadoNaoEncontradoException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.service.CadastroCidadeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeConverter cidadeConverter;

    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeConverter.toCollectionDTO(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable Long id) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        CidadeDTO cidadeDTO = cidadeConverter.toDTO(cidade);

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
                     .slash(cidadeDTO.getId()).withSelfRel());

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
                     .withRel("cidades"));

        cidadeDTO.add(WebMvcLinkBuilder.linkTo(EstadoController.class)
                     .slash(cidadeDTO.getEstado().getId()).withSelfRel());

        return cidadeDTO;
    }

    @ApiOperation("Cadastra uma cidade")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeConverter.toDomainObject(cidadeInput);
            cidade = cadastroCidade.salvar(cidade);

            CidadeDTO cidadeDTO = cidadeConverter.toDTO(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());

            return cidadeDTO;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid CidadeInput cidadeInput) {

        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

//		BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        cidadeConverter.copyToDomainObject(cidadeInput, cidadeAtual);

        try {

            return cidadeConverter.toDTO(cadastroCidade.salvar(cidadeAtual));

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroCidade.remover(id);
    }

}

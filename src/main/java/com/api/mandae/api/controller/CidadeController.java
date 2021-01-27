package com.api.mandae.api.controller;

import com.api.mandae.api.ResourceUriHelper;
import com.api.mandae.api.assembler.cidade.CidadeConverter;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.api.openapi.controller.CidadeControllerOpenApi;
import com.api.mandae.domain.exception.EstadoNaoEncontradoException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.service.CadastroCidadeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
    public CollectionModel<CidadeDTO> listar() {

        List<Cidade> todasCidades = cidadeRepository.findAll();

        List<CidadeDTO> cidadesDTO = cidadeConverter.toCollectionDTO(todasCidades);

        cidadesDTO.forEach(cidadeModel -> {
            cidadeModel.add(linkTo(methodOn(CidadeController.class)
                    .buscar(cidadeModel.getId())).withSelfRel());

            cidadeModel.add(linkTo(methodOn(CidadeController.class)
                    .listar()).withRel("cidades"));

            cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
                    .buscar(cidadeModel.getEstado().getId())).withSelfRel());
        });

        CollectionModel<CidadeDTO> cidadesCollectionModel = new CollectionModel<>(cidadesDTO);
        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());

        return cidadesCollectionModel;
    }

    @GetMapping("/{id}")
    public CidadeDTO buscar(@PathVariable Long id) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        CidadeDTO cidadeDTO = cidadeConverter.toDTO(cidade);

        //relacionando ao método
        cidadeDTO.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeDTO.getId())).withSelfRel());

        //relacionando ao endpoint
//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//                     .slash(cidadeDTO.getId()).withSelfRel());

        cidadeDTO.add(linkTo(methodOn(CidadeController.class).listar())
                .withRel("cidades"));

//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//                .withRel("cidades"));

        cidadeDTO.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeDTO.getEstado().getId())).withSelfRel());

//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(EstadoController.class)
//                .slash(cidadeDTO.getEstado().getId()).withSelfRel());

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

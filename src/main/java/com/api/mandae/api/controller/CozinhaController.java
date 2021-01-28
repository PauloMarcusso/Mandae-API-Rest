package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.cozinha.CozinhaConverter;
import com.api.mandae.api.model.CozinhaDTO;
import com.api.mandae.api.model.input.CozinhaInput;
import com.api.mandae.api.openapi.controller.CozinhaControllerOpenApi;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;
import com.api.mandae.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaConverter cozinhaConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;


    @GetMapping
    public PagedModel<CozinhaDTO> listar(Pageable pageable) {

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhaPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaConverter);

//        List<CozinhaDTO> cozinhaDTO = cozinhaConverter.toCollectionModel(cozinhasPage.getContent());
//        Page<CozinhaDTO> cozinhaDTOPage = new PageImpl<>(cozinhaDTO, pageable, cozinhasPage.getTotalElements());

        return cozinhaPagedModel;
    }

    @GetMapping("/{id}")
    public CozinhaDTO buscar(@PathVariable Long id) {
        return cozinhaConverter.toModel(cadastroCozinha.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinha = cozinhaConverter.toDomainObject(cozinhaInput);

        return cozinhaConverter.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);

        cozinhaConverter.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaConverter.toModel(cadastroCozinha.salvar(cozinhaAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinha.excluir(id);
    }
}

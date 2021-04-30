package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.cozinha.CozinhaConverter;
import com.api.mandae.api.model.CozinhaDTO;
import com.api.mandae.api.model.input.CozinhaInput;
import com.api.mandae.api.openapi.controller.CozinhaControllerOpenApi;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;
import com.api.mandae.domain.service.CadastroCozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaConverter cozinhaConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;


    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public PagedModel<CozinhaDTO> listar(Pageable pageable) {

        logger.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize());

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaDTO> cozinhaPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaConverter);

//        List<CozinhaDTO> cozinhaDTO = cozinhaConverter.toCollectionModel(cozinhasPage.getContent());
//        Page<CozinhaDTO> cozinhaDTOPage = new PageImpl<>(cozinhaDTO, pageable, cozinhasPage.getTotalElements());

        return cozinhaPagedModel;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public CozinhaDTO buscar(@PathVariable Long id) {
        return cozinhaConverter.toModel(cadastroCozinha.buscarOuFalhar(id));
    }

    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinha = cozinhaConverter.toDomainObject(cozinhaInput);

        return cozinhaConverter.toModel(cadastroCozinha.salvar(cozinha));
    }

    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @PutMapping("/{id}")
    public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);

        cozinhaConverter.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaConverter.toModel(cadastroCozinha.salvar(cozinhaAtual));
    }

    @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCozinha.excluir(id);
    }
}

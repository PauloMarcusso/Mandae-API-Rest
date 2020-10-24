package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.grupo.GrupoConverter;
import com.api.mandae.api.model.GrupoDTO;
import com.api.mandae.api.model.input.GrupoInput;
import com.api.mandae.domain.model.Grupo;
import com.api.mandae.domain.repository.GrupoRepository;
import com.api.mandae.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GruposController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoConverter grupoConverter;

    @GetMapping
    public List<GrupoDTO> listar(){
        return grupoConverter.toCollectionDTO(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id){
        return grupoConverter.toDTO(cadastroGrupo.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput){
        Grupo grupo = grupoConverter.toDomainObject(grupoInput);
        return grupoConverter.toDTO(cadastroGrupo.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoDTO atualizar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id){

        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);
        grupoConverter.copyToDomainObject(grupoInput, grupoAtual);

        return grupoConverter.toDTO(cadastroGrupo.salvar(grupoAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroGrupo.excluir(id);
    }
}

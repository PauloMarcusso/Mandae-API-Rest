package com.api.mandae.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.mandae.api.assembler.cozinha.CozinhaConverter;
import com.api.mandae.api.model.CozinhaDTO;
import com.api.mandae.api.model.input.CozinhaInput;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;
import com.api.mandae.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaConverter cozinhaConverter;
	

	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhaConverter.toCollectionDTO(cozinhaRepository.findAll());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		return cozinhaConverter.toDTO(cadastroCozinha.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaConverter.toDomainObject(cozinhaInput);
		
		return cozinhaConverter.toDTO(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
		
			Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
			
			cozinhaConverter.copyToDomainObject(cozinhaInput, cozinhaAtual);

			return cozinhaConverter.toDTO(cadastroCozinha.salvar(cozinhaAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinha.excluir(id);
	}
}

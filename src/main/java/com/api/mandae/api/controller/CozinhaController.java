package com.api.mandae.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.mandae.domain.exception.EntidadeEmUsoException;
import com.api.mandae.domain.exception.EntidadeNaoEncontradaException;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;
import com.api.mandae.domain.service.CadastroCozinhaService;

import lombok.experimental.Helper;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		try {
			Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);

			if (cozinhaAtual.isPresent()) {

				BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
				Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
				return ResponseEntity.ok(cozinhaSalva);
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> remover(@PathVariable Long id) {
//		try {
//			cadastroCozinha.excluir(id);
//			return ResponseEntity.noContent().build();
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//
//	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
			cadastroCozinha.excluir(id);
	}
}

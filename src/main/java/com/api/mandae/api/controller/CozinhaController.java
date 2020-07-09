package com.api.mandae.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		// caso o id não exista, devolve um status 404
		if (cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionar(@RequestBody Cozinha cozinha) {
		cozinhaRepository.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

		Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
		if (cozinhaAtual != null) {

//		cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cozinhaRepository.salvar(cozinhaAtual);

			return ResponseEntity.ok(cozinhaAtual);
		}
		return ResponseEntity.notFound().build();
	}
}

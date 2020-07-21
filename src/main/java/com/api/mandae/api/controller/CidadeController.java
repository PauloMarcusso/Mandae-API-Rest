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
import org.springframework.web.bind.annotation.RestController;

import com.api.mandae.domain.exception.EntidadeEmUsoException;
import com.api.mandae.domain.exception.EntidadeNaoEncontradaException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {

		Optional<Cidade> cidade = cidadeRepository.findById(id);
		if (cidade.isPresent()) {

			return ResponseEntity.ok(cidade.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidade.salvar(cidade);
			return ResponseEntity.ok(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {

		try {
			Optional<Cidade> cidadeAtual = cidadeRepository.findById(id);
			
			if (cidadeAtual.isPresent()) {
				
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual.get());

				return ResponseEntity.ok(cidadeSalva);
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> excluir(@PathVariable Long id){
		try {
			cadastroCidade.remover(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}

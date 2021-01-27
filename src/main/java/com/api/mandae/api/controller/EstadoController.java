package com.api.mandae.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.api.mandae.api.openapi.controller.EstadoControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.mandae.api.assembler.estado.EstadoConverter;
import com.api.mandae.api.model.EstadoDTO;
import com.api.mandae.api.model.input.EstadoInput;
import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.EstadoRepository;
import com.api.mandae.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoConverter estadoConverter;

	@GetMapping
	public List<EstadoDTO> listar() {
		return estadoConverter.toCollectionDTO(estadoRepository.findAll());
	}

	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoConverter.toModel(cadastroEstado.buscarOuFalhar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoConverter.toDomainObject(estadoInput);
		
		return estadoConverter.toModel(cadastroEstado.salvar(estado));
	}

	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {

		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
		
		estadoConverter.copyToDomainObject(estadoInput, estadoAtual);

		return estadoConverter.toModel(cadastroEstado.salvar(estadoAtual));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstado.excluir(id);
	}

}

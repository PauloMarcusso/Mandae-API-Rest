package com.api.mandae.api.controller;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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

import com.api.mandae.api.assembler.cidade.CidadeConverter;
import com.api.mandae.api.model.CidadeDTO;
import com.api.mandae.api.model.input.CidadeInput;
import com.api.mandae.domain.exception.EstadoNaoEncontradoException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.service.CadastroCidadeService;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeConverter cidadeConverter;

	@ApiOperation("Lista todas as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeConverter.toCollectionDTO(cidadeRepository.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{id}")
	public CidadeDTO buscar(@PathVariable Long id) {
		return cidadeConverter.toDTO(cadastroCidade.buscarOuFalhar(id));
	}

	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeConverter.toDomainObject(cidadeInput);
			
			return cidadeConverter.toDTO(cadastroCidade.salvar(cidade));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
		
//		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		cidadeConverter.copyToDomainObject(cidadeInput, cidadeAtual);
		
		try {
			
			return cidadeConverter.toDTO(cadastroCidade.salvar(cidadeAtual));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation("Deleta uma cidade por ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cadastroCidade.remover(id);
	}

}

package com.api.mandae.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.mandae.domain.exception.CozinhaNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.RestauranteNaoEncontradoException;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		try {
			restaurante.setCozinha(cozinha);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long id){
		Restaurante restauranteAtual = buscarOuFalhar(id);
		restauranteAtual.ativar();
	}

	@Transactional
	public void inativar(Long id){
		Restaurante restauranteAtual = buscarOuFalhar(id);
		restauranteAtual.inativar();
	}

	@Transactional
	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}
}

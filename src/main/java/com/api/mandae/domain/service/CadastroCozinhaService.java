package com.api.mandae.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
}

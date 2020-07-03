package com.api.mandae.domain.repository;

import java.util.List;

import com.api.mandae.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade salvar(Cidade cidade);
	Cidade buscar(Long id);
	void remover(Cidade cidade);
}

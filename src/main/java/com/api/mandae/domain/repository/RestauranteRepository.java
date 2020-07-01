package com.api.mandae.domain.repository;

import java.util.List;

import com.api.mandae.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);
}

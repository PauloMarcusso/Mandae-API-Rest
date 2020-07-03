package com.api.mandae.domain.repository;

import java.util.List;

import com.api.mandae.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado salvar(Estado estado);
	Estado buscar(Long id);
	void remover(Estado estado);
}

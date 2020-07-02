package com.api.mandae.domain.repository;

import java.util.List;

import com.api.mandae.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listar();
	FormaPagamento salvar(FormaPagamento formaPagamento);
	FormaPagamento buscar(Long id);
	void remover(FormaPagamento formaPagamento);
}

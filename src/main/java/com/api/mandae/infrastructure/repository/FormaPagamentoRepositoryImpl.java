package com.api.mandae.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	EntityManager manager;

	@Override
	public List<FormaPagamento> listar() {

		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}

	@Transactional
	@Override
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = this.buscar(formaPagamento.getId());
		manager.remove(formaPagamento);
	}

}

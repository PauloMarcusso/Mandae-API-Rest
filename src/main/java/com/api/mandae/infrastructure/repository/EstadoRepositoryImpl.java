package com.api.mandae.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Transactional
	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}

	@Override
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}

	@Transactional
	@Override
	public void remover(Estado estado) {
		estado = this.buscar(estado.getId());
		manager.remove(estado);
	}

}

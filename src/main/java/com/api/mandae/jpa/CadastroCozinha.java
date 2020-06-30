package com.api.mandae.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.api.mandae.domain.model.Cozinha;

@Component
public class CadastroCozinha {

	@PersistenceContext
	private EntityManager manager;

	public List<Cozinha> listar() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	
	
	
	
	
	
	
	
	
}

package com.api.mandae.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository{
	
	@Autowired
	private EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Restaurante adicionar(Restaurante restaurante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Restaurante restaurante) {
		// TODO Auto-generated method stub
		
	}

}

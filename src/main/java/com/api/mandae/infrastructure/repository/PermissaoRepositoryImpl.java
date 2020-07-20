package com.api.mandae.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.mandae.domain.model.Permissao;
import com.api.mandae.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Transactional
	@Override
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}

	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}

	@Transactional
	@Override
	public void remover(Permissao permissao) {
		permissao = this.buscar(permissao.getId());
		manager.remove(permissao);
	}
	
	
}

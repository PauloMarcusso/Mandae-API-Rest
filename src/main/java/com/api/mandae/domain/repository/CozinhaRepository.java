package com.api.mandae.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.mandae.domain.model.Cozinha;
import com.sun.xml.bind.v2.model.core.ID;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{

//	List<Cozinha> consultarPorNome(String nome);
	
}

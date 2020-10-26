package com.api.mandae.domain.repository;

import com.api.mandae.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);

}

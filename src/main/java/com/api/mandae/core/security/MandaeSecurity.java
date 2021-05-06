package com.api.mandae.core.security;

import com.api.mandae.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class MandaeSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {

        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("usuario_id");
    }

    public boolean gerenciaRestaurante(Long restauranteId) {

        if (restauranteId == null) {
            return false;
        }

        return restauranteRepository.existsResponsavel(restauranteId, getUserId());
    }

}

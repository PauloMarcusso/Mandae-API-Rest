package com.api.mandae.jpa.restaurante;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.mandae.MandaeApplication;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(MandaeApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("AAEEOOOOO");
		
		restaurante = restauranteRepository.salvar(restaurante);
		
		System.out.printf("O nome do restaurante de id %d agora é %s %n", restaurante.getId(), restaurante.getNome());
	}

}
package com.api.mandae.jpa.estado;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.mandae.MandaeApplication;
import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.EstadoRepository;

public class InclusaoEstadoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(MandaeApplication.class)
				.web(WebApplicationType.NONE).run(args);

		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

		Estado estado = new Estado();
		estado.setNome("São Paulo");
		estado = estadoRepository.salvar(estado);
		
		System.out.println("Novo Estado adicionado: " + estado.getNome());
	}
}

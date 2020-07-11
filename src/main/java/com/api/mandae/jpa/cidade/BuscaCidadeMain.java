package com.api.mandae.jpa.cidade;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.mandae.MandaeApplication;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.repository.CidadeRepository;

public class BuscaCidadeMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(MandaeApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		
		Cidade cidade = cidadeRepository.buscar(1L);
		
		System.out.printf("%d - %s %n", cidade.getId(), cidade.getNome());
	}
}
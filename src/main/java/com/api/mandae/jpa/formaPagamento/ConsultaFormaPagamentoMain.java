package com.api.mandae.jpa.formaPagamento;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.mandae.MandaeApplication;
import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(MandaeApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.listar();
		
		for (FormaPagamento formaPagamento : formasPagamento) {
			System.out.printf("%s %n", formaPagamento.getDescricao());
		}
	}
}

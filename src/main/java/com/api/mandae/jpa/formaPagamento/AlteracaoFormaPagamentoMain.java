package com.api.mandae.jpa.formaPagamento;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.api.mandae.MandaeApplication;
import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.repository.FormaPagamentoRepository;

public class AlteracaoFormaPagamentoMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(MandaeApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setId(1L);
		formaPagamento.setDescricao("fiado");
		
		formaPagamento = formaPagamentoRepository.salvar(formaPagamento);
		
		System.out.println("Forma de pagamento alterada:" + formaPagamento.getDescricao());
	}
}

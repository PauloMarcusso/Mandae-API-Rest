package com.api.mandae;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.mandae.domain.exception.CozinhaNaoEncontradaException;
import com.api.mandae.domain.exception.EntidadeEmUsoException;
import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Jamaicana");
		
		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalharAoCadastrarCozinhaSemNome() throws ConstraintViolationException{
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		Assertions.assertThrows(ConstraintViolationException.class, () ->{
			
			cadastroCozinha.salvar(novaCozinha);
		});
	}
	
	@Test
	public void deveFalharQuandoExcluirCozinhaEmUso() {
		
		assertThrows(EntidadeEmUsoException.class, ()->{
			cadastroCozinha.excluir(1L);
		});
	}
	
	@Test
	public void deveFalharQuandoExcluirCozinhaInexistente() {
		
		assertThrows(CozinhaNaoEncontradaException.class, ()->{
			cadastroCozinha.excluir(1000L);
		});
	}
}

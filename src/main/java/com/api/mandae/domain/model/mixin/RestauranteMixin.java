package com.api.mandae.domain.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Endereco;
import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RestauranteMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}

package com.api.mandae.domain.model;

public enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado");

	private String descricao;

	StatusPedido(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao(){
		return this.descricao;
	}


}

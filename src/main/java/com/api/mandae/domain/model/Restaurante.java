package com.api.mandae.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.api.mandae.core.validation.Groups.CozinhaId;
import com.api.mandae.core.validation.TaxaFrete;
import com.api.mandae.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank//(message = "Nome é obrigatório")
	@Column( nullable = false)
	private String nome;

	@NotNull
	@TaxaFrete
//	@Multiplo(numero = 5)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@Valid
	@ManyToOne //(fetch = FetchType.LAZY)
	@NotNull
	@ConvertGroup(from = Default.class, to = CozinhaId.class)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;

	private Boolean ativo = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany 
	@JoinTable(name = "restaurante_forma_pagamento",
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	public void ativar(){
		setAtivo(true);
	}

	public void inativar(){
		setAtivo(false);
	}
}

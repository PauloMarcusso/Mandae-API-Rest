package com.api.mandae.domain.model;

import com.api.mandae.core.validation.Groups.CozinhaId;
import com.api.mandae.core.validation.TaxaFrete;
import com.api.mandae.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(nullable = false)
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

    private Boolean aberto = Boolean.TRUE;

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
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().add(formaPagamento);
    }

    public boolean adicionarUsuarioResponsavel(Usuario usuario) {
        return getUsuarios().add(usuario);
    }

    public boolean removerUsuarioResponsavel(Usuario usuario) {
        return getUsuarios().remove(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().contains(formaPagamento);
    }
    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento){
        return !aceitaFormaPagamento(formaPagamento);
    }

    public boolean isAberto(){
        return this.aberto;
    }

    public boolean isFechado(){
        return !isAberto();
    }

    public boolean isAtivo(){
        return this.ativo;
    }

    public boolean isInativo(){
        return !isAtivo();
    }

    public boolean aberturaPermitida(){
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida(){
        return isInativo();
    }

    public boolean inativacaoPermitida(){
        return isAtivo();
    }

    public boolean fechamentoPertimido(){
        return isAberto();
    }


}

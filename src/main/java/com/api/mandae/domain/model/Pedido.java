package com.api.mandae.domain.model;

import com.api.mandae.domain.event.PedidoCanceladoEvent;
import com.api.mandae.domain.event.PedidoConfirmadoEvent;
import com.api.mandae.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Data
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {

        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(pedido -> pedido.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public boolean podeSerConfirmado(){
        return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerCancelado(){
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    public boolean podeSerEntregue(){
        return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());

        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregue() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());

        registerEvent(new PedidoCanceladoEvent(this));
    }
	private void setStatus(StatusPedido novoStatus){
    	if(getStatus().naoPodeAlterarPara(novoStatus)){
			throw new NegocioException(
					String.format("Status do pedido %d não pode ser alterado de %s para %s", getCodigo()    ,
							getStatus().getDescricao(), novoStatus.getDescricao())
			);
		}
    	this.status = novoStatus;
	}

	@PrePersist
	private void gerarCodido(){
        setCodigo(UUID.randomUUID().toString());
    }

}

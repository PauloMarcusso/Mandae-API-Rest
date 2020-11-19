package com.api.mandae.infrastructure.repository.spec;

import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){

		return (root, query, builder) -> {

			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");

			var predicates = new ArrayList<Predicate>();

			if( filtro.getClienteId() != null){
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}

			if( filtro.getRestauranteId() != null){
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}

			if( filtro.getDataCriacaoInicio() != null){
				predicates.add(builder.greaterThan(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			if( filtro.getDataCriacaoFim() != null){
				predicates.add(builder.lessThan(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}

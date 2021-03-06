package com.api.mandae.api.assembler;

import java.util.List;

public interface Converter<T, S, U> {

	/*
	 * Exemplos:
	 * T - Restaurante
	 * S - RestauranteDTO
	 * U - RestauranteInput
	 */
	public S toModel(T domain);
	
	public List<S> toCollectionDTO(List<T> list);
	
	public T toDomainObject(U inputDomain);
	
	public void copyToDomainObject(U inputDomain, T domain);

}

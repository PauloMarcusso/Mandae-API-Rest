package com.api.mandae.core.jackson;

import org.springframework.stereotype.Component;

import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.model.mixin.RestauranteMixin;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
	}

}

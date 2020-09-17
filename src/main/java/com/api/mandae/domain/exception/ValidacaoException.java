package com.api.mandae.domain.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;
}

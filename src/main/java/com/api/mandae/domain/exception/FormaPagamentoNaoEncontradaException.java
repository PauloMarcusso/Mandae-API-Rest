package com.api.mandae.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "FormaPagamento não encontrada")
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("FormaPagamento com o código %d não encontrada", formaPagamentoId));
    }
}

package com.api.mandae.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Grupo não encontrado")
public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
    public GrupoNaoEncontradoException(Long grupoId){
        this(String.format("Grupo com o código %d não encontrado", grupoId));
    }
}

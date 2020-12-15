package com.api.mandae.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nomeArquivo){
        return UUID.randomUUID().toString() + "_" + nomeArquivo;
    }

    @Getter
    @Setter
    @Builder
    class NovaFoto{

        private String nomeArquivo;
        private InputStream inputStream;
    }
}

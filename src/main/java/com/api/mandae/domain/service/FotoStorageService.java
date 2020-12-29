package com.api.mandae.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto){
        this.armazenar(novaFoto);

        if(nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }
    default String gerarNomeArquivo(String nomeArquivo){
        return UUID.randomUUID().toString() + "_" + nomeArquivo;
    }

    @Getter
    @Builder
    class NovaFoto{

        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {

        private InputStream inputStream;
        private String url;

        public boolean temUrl(){
            return url != null;
        }

        public boolean temInputStream(){
            return inputStream != null;
        }
    }
}

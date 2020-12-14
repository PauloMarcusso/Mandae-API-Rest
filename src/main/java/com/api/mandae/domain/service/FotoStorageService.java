package com.api.mandae.domain.service;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    @Getter
    @Setter
    class NovaFoto{

        private String nomeArquivo;
        private InputStream inputStream;
    }
}

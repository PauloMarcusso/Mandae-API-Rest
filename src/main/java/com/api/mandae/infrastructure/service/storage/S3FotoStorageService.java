package com.api.mandae.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.api.mandae.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Override public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override public void armazenar(NovaFoto novaFoto) {

    }

    @Override public void remover(String nomeArquivo) {

    }

    @Override public void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {

    }

    @Override public String gerarNomeArquivo(String nomeArquivo) {
        return null;
    }
}

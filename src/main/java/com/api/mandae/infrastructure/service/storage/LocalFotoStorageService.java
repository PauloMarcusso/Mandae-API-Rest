package com.api.mandae.infrastructure.service.storage;

import com.api.mandae.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${mandae.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {

            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));

        } catch (Exception e) {
            throw new StorageException("Não ofi possível armazenar arquivo", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}

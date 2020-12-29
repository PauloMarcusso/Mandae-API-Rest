package com.api.mandae.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.api.mandae.domain.service.FotoStorageService;
import com.api.mandae.infrastructure.service.storage.LocalFotoStorageService;
import com.api.mandae.infrastructure.service.storage.S3FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {

        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreto()
        );

        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                    .withRegion(storageProperties.getS3().getRegion())
                                    .build();
    }

    @Bean
    public FotoStorageService fotoStorageService(){
        if(StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())){
            return new S3FotoStorageService();
        }else{
            return new LocalFotoStorageService();
        }
    }
}

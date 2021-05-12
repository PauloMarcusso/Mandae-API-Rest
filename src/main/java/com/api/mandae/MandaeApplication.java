package com.api.mandae;

import java.util.TimeZone;

import com.api.mandae.core.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.api.mandae.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class MandaeApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		var app = new SpringApplication(MandaeApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);

//		SpringApplication.run(MandaeApplication.class, args);
	}

}

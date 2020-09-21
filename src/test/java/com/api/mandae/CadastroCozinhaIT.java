package com.api.mandae;

import static io.restassured.RestAssured.given;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
//		
		flyway.migrate();
	}

	@Test
	public void deveRetornarStatus200QuandoConsultarCozinha() {
	
		given()
		.accept(ContentType.JSON)
//			.basePath("/cozinhas")
//			.port(port)
		.when()
			.get()
			.then()
			.statusCode(200);
	}
	
	@Test
	public void deveConter4CozinhasQuandoConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(4))
			.body("nome", Matchers.hasItems("Tailandesa", "Indiana"));
	}
	
	@Test
	public void deveRetornarStatus201QuandoCadastrasCozinha() {
		
		given()
			.body("{ \"nome\": \"Chinesa\"}")
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
}

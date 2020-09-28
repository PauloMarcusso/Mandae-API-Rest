package com.api.mandae.cidade;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.repository.EstadoRepository;
import com.api.mandae.util.DatabaseCleaner;
import com.api.mandae.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCidadeIT {

	private static final String DADOS_INVÁLIDOS = "Dados inválidos";

	private static final int CIDADE_ID_INEXISTENTE = 200;

	@LocalServerPort
	private int port;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Cidade cidadeTest;
	private Estado estadoTest;

	private String jsonCidadeTestDadosCorretos;
	private String jsonCidadeSemEstado;
	private String jsonCidadeTestSemNome; 


	
	@BeforeEach
	public void setup() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.basePath = "/cidades";
		RestAssured.port = port;
		
		jsonCidadeTestDadosCorretos = ResourceUtils.getContentFromResource("/json/correto/cidade-test.json");
		jsonCidadeSemEstado = ResourceUtils.getContentFromResource("/json/incorreto/cidade-test-sem-estado.json");
		jsonCidadeTestSemNome = ResourceUtils.getContentFromResource("/json/incorreto/cidade-test-sem-nome.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200AoConsultarCidades() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveRetornar200AoConsultarCidadeExistente() {
		
		given()
			.accept(ContentType.JSON)
			.pathParam("cidadeId", cidadeTest.getId())
		.when()
			.basePath("/{cidadeId}")
		.then()
			.statusCode(200)
			.body("nome", equalTo("Cabobró"));
	}
	
	@Test
	public void deveRetornarStatus404AoConsultarCidadeInexistente() {
		
		given()
			.accept(ContentType.JSON)
			.pathParam("cidadeId", CIDADE_ID_INEXISTENTE)
		.when()
			.get("/{cidadeId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
			.body("title", equalTo("Recurso não encontrado"));
	}
	
	@Test
	public void deveRetornarStatus201AoCadastrarCidadeCorreta() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonCidadeTestDadosCorretos)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus404AoCadastrarCidadeSemNome() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonCidadeTestSemNome)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVÁLIDOS));
	}
	
	@Test
	public void deveRetornarStatus404AoCadastrarCidadeSemEstado() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonCidadeSemEstado)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVÁLIDOS));
	}
	
	public void prepararDados() {
		
		estadoTest = new Estado();
		estadoTest.setNome("Massaxuxets");
		
		cidadeTest = new Cidade();
		cidadeTest.setNome("Cabobró");
		cidadeTest.setEstado(estadoTest);
		
		estadoRepository.save(estadoTest);
		cidadeRepository.save(cidadeTest);
		
	}
}

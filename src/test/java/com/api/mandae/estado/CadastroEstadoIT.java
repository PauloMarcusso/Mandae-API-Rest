package com.api.mandae.estado;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.EstadoRepository;
import com.api.mandae.util.DatabaseCleaner;
import com.api.mandae.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroEstadoIT {

	private static final int ESTADO_ID_INEXISTENTE = 200;

	@LocalServerPort
	private int port;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Estado estadoTest;
	
	private String jsonEstadoTestCorreto;
	private String jsonEstadoTestSemNome;

	
	@BeforeEach
	public void setup() {
		
		RestAssured.port = port;
		RestAssured.basePath = "/estados";
		
		jsonEstadoTestCorreto = ResourceUtils.getContentFromResource("/json/correto/estado-test.json");
		jsonEstadoTestSemNome = ResourceUtils.getContentFromResource("/json/incorreto/estado-test-sem-nome.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200AoConsultarEstados() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretosQuandoConsultarEstadoExistente() {
		given()
			.accept(ContentType.JSON)
			.pathParam("estadoId", estadoTest.getId())
		.when()
			.get("/{estadoId}")
		.then()
			.statusCode(200)
			.body("nome", equalTo("São Paulo"));
	}
	
	@Test
	public void deveRetornarStatus404AoConsultarEstadoInexistente() {
		given()
			.accept(ContentType.JSON)
			.pathParam("estadoId", ESTADO_ID_INEXISTENTE)
		.when()
			.get("/{estadoId}")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void deveRetornarStatus201AoCadastrarEstado() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonEstadoTestCorreto)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveRetornar400AoCadastrarEstadoSemNome() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonEstadoTestSemNome)
		.when()
			.post()
		.then()
			.statusCode(400);
	}
	
	public void prepararDados(){
		
		estadoTest = new Estado();
		estadoTest.setNome("São Paulo");
		
		estadoRepository.save(estadoTest);
	}
	
}

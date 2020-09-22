package com.api.mandae;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.api.mandae.domain.model.Cozinha;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.CozinhaRepository;
import com.api.mandae.domain.repository.RestauranteRepository;
import com.api.mandae.util.DatabaseCleaner;
import com.api.mandae.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	private static final String MENSAGEM_INCOMPREENSÍVEL = "Mensagem incompreensível";

	private static final String DADOS_INVÁLIDOS = "Dados inválidos";

	private static final int RESTAURANTE_ID_INEXISTENTE = 200;

	@LocalServerPort
	private int port;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Restaurante restauranteTest;

	private String jsonRestauranteTestCorreto;
	private String jsonRestauranteSemTaxaFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteCozinhaInexistente;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.basePath = "/restaurantes";
		RestAssured.port = port;
		
		//Para post
		jsonRestauranteTestCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-test.json");
		jsonRestauranteSemTaxaFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-test-sem-taxafrete.json");
		jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-test-sem-cozinha");
		jsonRestauranteCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-test-id-inexistente");
		
		databaseCleaner.clearTables();
		prepararDados();
		
	}

	@Test
	public void deveRetornar200QuandoConsultarRestaurante() {
	
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretosQuandoConsultarCozinhaExistente() {
		
		given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", restauranteTest.getId())
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(200)
			.body("nome", equalTo(restauranteTest.getNome()));
	}
	
	@Test
	public void deveRetornar404QuandoNaoExistirRestaurante() {
		given()
			.accept(ContentType.JSON)
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void deveCadastrarRestauranteComDadosCorretosERetornarStatus201() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteTestCorreto)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test()
	public void deveFalharAoCadastrarRestauranteSemTaxaFrete() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteSemTaxaFrete)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVÁLIDOS));
	}
	
	@Test
	public void deveRetornar400AoCadastrarResturanteSemCozinha() {
		
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteSemCozinha)
		.when()
			.post()
		.then()
			.statusCode(400)
			.body("title", equalTo(MENSAGEM_INCOMPREENSÍVEL));
	}
	
	@Test
	public void deveRetornar400AoCadastrarRestauranteComCozinhaInexistente() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonRestauranteCozinhaInexistente)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(MENSAGEM_INCOMPREENSÍVEL)); 
	}
	
	public void prepararDados() {
		
		Cozinha cozinhaBrasileira = new Cozinha();
		cozinhaBrasileira.setNome("Cozinha Brasileira");
		cozinhaRepository.save(cozinhaBrasileira);
		
		restauranteTest = new Restaurante();
		restauranteTest.setNome("Restaurante Brasileiro");
		restauranteTest.setTaxaFrete(new BigDecimal(10));
		restauranteTest.setCozinha(cozinhaBrasileira);
		
		restauranteRepository.save(restauranteTest);
		
		
	}
}

package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.restaurante.*;
import com.api.mandae.api.model.RestauranteApenasNomeDTO;
import com.api.mandae.api.model.RestauranteBasicoDTO;
import com.api.mandae.api.model.RestauranteDTO;
import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.api.openapi.controller.RestauranteControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.domain.exception.CidadeNaoEncontradaException;
import com.api.mandae.domain.exception.CozinhaNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.RestauranteNaoEncontradoException;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.RestauranteRepository;
import com.api.mandae.domain.service.CadastroRestauranteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteConverter restauranteConverter;

    @Autowired
    private RestauranteBasicoConverter restauranteBasicoConverter;

    @Autowired
    private RestauranteApenasNomeConverter restauranteApenasNomeConverter;


    @ApiOperation(value = "Lista Restaurantes")
//    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    @CheckSecurity.Restaurantes.PodeConsultar
    public CollectionModel<RestauranteBasicoDTO> listar() {
        return restauranteBasicoConverter.toCollectionModel(restauranteRepository.findAll());
    }

    @ApiOperation(value = "Lista Restaurantes", hidden = true)
//    @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeDTO> listarApenasNomes() {
        return restauranteApenasNomeConverter.toCollectionModel(restauranteRepository.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return restauranteConverter.toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {

            Restaurante restaurante = restauranteConverter.toDomainObject(restauranteInput);

            return restauranteConverter.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

//		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        restauranteConverter.copyToDomainObject(restauranteInput, restauranteAtual);

//		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
//				"produtos");

        try {

            return restauranteConverter.toModel(cadastroRestaurante.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        cadastroRestaurante.inativar(id);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        cadastroRestaurante.ativar(id);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<Void> abertura(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrirRestaurante(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fechamento(@PathVariable Long restauranteId) {
        cadastroRestaurante.fecharRestaurante(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//        List<RestauranteDTO> restaurantesModel = restauranteConverter.toCollectionDTO(restaurantes);
//
//        MappingJacksonValue restaurantesWraper = new MappingJacksonValue(restaurantesModel);
//
//        restaurantesWraper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("apenas-nome".equals(projecao)) {
//            restaurantesWraper.setSerializationView(RestauranteView.ApenasNome.class);
//        } else if ("completo".equals(projecao)) {
//            restaurantesWraper.setSerializationView(null);
//
//        }
//
//        return restaurantesWraper;
//    }

//    @PatchMapping("/{id}")
//    public RestauranteDTO atualizaParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
//                                          HttpServletRequest request) {
//
//        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
//
//        merge(campos, restauranteAtual, request);
//
//        validate(restauranteAtual, "restaurante");
//
//		return atualizar(id, restauranteAtual);
//        return null;
//    }

//    private void validate(Restaurante restaurante, String objectName) {
//        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//
//        validator.validate(restaurante, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            throw new ValidacaoException(bindingResult);
//        }
//    }
//
//    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
//
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//        try {
//            // pega o tipo da variável e inputa no novo objeto criado
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//
//            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//
//                // pegando os atributos do Restaurante
//                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//
//                // acessa um atributo privado
//                field.setAccessible(true);
//
//                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//
//                ReflectionUtils.setField(field, restauranteDestino, novoValor);
//            });
//        } catch (IllegalArgumentException e) {
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//    }

}

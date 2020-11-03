package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.restaurante.RestauranteConverter;
import com.api.mandae.api.assembler.restaurante.RestauranteDTOAssembler;
import com.api.mandae.api.assembler.restaurante.RestauranteInputDisassembler;
import com.api.mandae.api.model.RestauranteDTO;
import com.api.mandae.api.model.input.RestauranteInput;
import com.api.mandae.domain.exception.CidadeNaoEncontradaException;
import com.api.mandae.domain.exception.CozinhaNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.ValidacaoException;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.RestauranteRepository;
import com.api.mandae.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

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

    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return restauranteDTOAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {

            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteDTOAssembler.toDTO(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

//		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        restauranteConverter.copyToDomainObject(restauranteInput, restauranteAtual);

//		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
//				"produtos");

        try {

            return restauranteDTOAssembler.toDTO(cadastroRestaurante.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        cadastroRestaurante.inativar(id);
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        cadastroRestaurante.ativar(id);
    }

    @PutMapping("/{restauranteId}/abertura")
    public void abertura(@PathVariable Long restauranteId){
    	cadastroRestaurante.abrirRestaurante(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
    public void fechamento(@PathVariable Long restauranteId){
        cadastroRestaurante.fecharRestaurante(restauranteId);
    }


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

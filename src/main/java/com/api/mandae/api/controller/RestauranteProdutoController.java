package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.produto.ProdutoConverter;
import com.api.mandae.api.model.ProdutoDTO;
import com.api.mandae.api.model.input.ProdutoInput;
import com.api.mandae.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.api.mandae.core.security.CheckSecurity;
import com.api.mandae.domain.model.Produto;
import com.api.mandae.domain.model.Restaurante;
import com.api.mandae.domain.repository.ProdutoRepository;
import com.api.mandae.domain.service.CadastroProdutoService;
import com.api.mandae.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoConverter produtoConverter;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId,
            @RequestParam(required = false) Boolean incluirInativos) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoConverter.toCollectionModel(todosProdutos);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        return produtoConverter.toModel(produto);

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {

        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoConverter.toDomainObject(produtoInput);

        produto.setRestaurante(restaurante);

        produto = cadastroProduto.salvar(produto);

        return produtoConverter.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {

        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoConverter.copyToDomainObject(produtoInput, produtoAtual);

        cadastroProduto.salvar(produtoAtual);

        return produtoConverter.toModel(produtoAtual);
    }
}

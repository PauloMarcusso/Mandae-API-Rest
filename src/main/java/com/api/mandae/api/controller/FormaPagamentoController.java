package com.api.mandae.api.controller;

import com.api.mandae.api.assembler.formapagamento.FormaPagamentoConverter;
import com.api.mandae.api.model.FormaPagamentoDTO;
import com.api.mandae.api.model.input.FormaPagamentoInput;
import com.api.mandae.domain.model.FormaPagamento;
import com.api.mandae.domain.repository.FormaPagamentoRepository;
import com.api.mandae.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoConverter formaPagamentoConverter;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<FormaPagamentoDTO>> listar() {
                List<FormaPagamentoDTO> todasFormasPagamentos =
                        formaPagamentoConverter.toCollectionDTO(formaPagamentoRepository.findAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(todasFormasPagamentos);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FormaPagamentoDTO buscar(@PathVariable Long id) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);
        return formaPagamentoConverter.toDTO(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamento = formaPagamentoConverter.toDomainObject(formaPagamentoInput);

        return formaPagamentoConverter.toDTO(cadastroFormaPagamento.salvar(formaPagamento));

    }


    @PutMapping("/{id}")
    public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInput formaPagamentoInput) {

        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);

        formaPagamentoConverter.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        return formaPagamentoConverter.toDTO(cadastroFormaPagamento.salvar(formaPagamentoAtual));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        cadastroFormaPagamento.excluir(id);
    }


}

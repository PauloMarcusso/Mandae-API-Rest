package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.CozinhaNaoEncontradaException;
import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.RestauranteNaoEncontradoException;
import com.api.mandae.domain.model.*;
import com.api.mandae.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;


    @Transactional
    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        try {
            restaurante.setCozinha(cozinha);
            restaurante.getEndereco().setCidade(cidade);

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
        ;

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        restaurante.adicionarUsuarioResponsavel(usuario);
    }

    @Transactional
    public void desassociarUsuarioResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
        restaurante.removerUsuarioResponsavel(usuario);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.inativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds){
        restauranteIds.forEach(this::ativar);
    }
    @Transactional
    public void inativar(List<Long> restauranteIds){
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void abrirRestaurante(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fecharRestaurante(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.fechar();
    }
}

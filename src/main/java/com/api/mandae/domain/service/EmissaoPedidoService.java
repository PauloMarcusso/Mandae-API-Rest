package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.NegocioException;
import com.api.mandae.domain.exception.PedidoNaoEncontradoException;
import com.api.mandae.domain.model.*;
import com.api.mandae.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Transactional
    public Pedido emitir(Pedido pedido) {

        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar((pedido.getRestaurante().getId()));
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(
                    String.format("Forma de pagamento '%s' não é aceita pelo restaurante.", formaPagamento.getDescricao()));
        }
    }


    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(
                item -> {
                    Produto produto = cadastroProduto.buscarOuFalhar(
                            pedido.getRestaurante().getId(), item.getProduto().getId());

                    item.setPedido(pedido);
                    item.setProduto(produto);
                    item.setPrecoUnitario(produto.getPreco());
                });
    }


    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() ->
                new PedidoNaoEncontradoException(
                        String.format("Pedido com código %d não encontrado", pedidoId)));

    }

}

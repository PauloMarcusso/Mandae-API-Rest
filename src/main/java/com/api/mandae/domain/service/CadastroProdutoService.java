package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.ProdutoNaoEncontradoException;
import com.api.mandae.domain.model.Produto;
import com.api.mandae.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId){
        return produtoRepository.findById(restauranteId,produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradoException(restauranteId,produtoId));
    }

    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

}

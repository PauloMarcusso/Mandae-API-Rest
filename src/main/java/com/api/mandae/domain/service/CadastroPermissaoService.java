package com.api.mandae.domain.service;

import com.api.mandae.domain.exception.PermissaoNaoEncontradaException;
import com.api.mandae.domain.model.Permissao;
import com.api.mandae.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId){
        return permissaoRepository.findById(permissaoId).orElseThrow(
                () -> new PermissaoNaoEncontradaException(permissaoId));
    }
}

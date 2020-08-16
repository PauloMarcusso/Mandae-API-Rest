package com.api.mandae.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.mandae.domain.exception.CidadeNaoEncontradaException;
import com.api.mandae.domain.exception.EntidadeEmUsoException;
import com.api.mandae.domain.model.Cidade;
import com.api.mandae.domain.model.Estado;
import com.api.mandae.domain.repository.CidadeRepository;
import com.api.mandae.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;

	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
	}
}

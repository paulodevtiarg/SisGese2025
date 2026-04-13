package br.com.sysgese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Funcao;
import br.com.sysgese.repository.FuncaoRepository;

@Service
public class FuncaoService {
	@Autowired
	private FuncaoRepository funcaoRepository;
	
	public Page<Funcao> buscar(String descricao, Integer[]status, Pageable pageable ){
		
		return funcaoRepository.findByDescricaoContainingIgnoreCaseAndStatusIn(descricao, status, pageable);
		
		
	}

}

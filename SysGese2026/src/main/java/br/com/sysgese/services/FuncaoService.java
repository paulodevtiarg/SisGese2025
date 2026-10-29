package br.com.sysgese.services;

import br.com.sysgese.dtos.CargoDTO;
import br.com.sysgese.dtos.FuncaoDTO;
import br.com.sysgese.models.Cargo;
import br.com.sysgese.specifications.FuncaoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	public Page<Funcao> buscarComFiltro(
			FuncaoDTO filtro,
			Pageable pageable) {

		Specification<Funcao> spec = Specification.allOf();

// 🔥 Filtro de descrição
		if (filtro.getFiltroDescricao() != null) {
			spec = spec.and(FuncaoSpecification.descricao(filtro.getFiltroDescricao()));
		}

// 🔥 Filtro de status (se o seu filtroStatus for String, você precisa converter para Integer)
		if (filtro.getFiltroStatus() != null && !filtro.getFiltroStatus().isBlank()) {
			// Se no HTML o status for "1" ou "0"
			Integer statusInt = Integer.valueOf(filtro.getFiltroStatus());
			spec = spec.and(FuncaoSpecification.status(statusInt));
		}

		return funcaoRepository.findAll(spec, pageable);
	}

}

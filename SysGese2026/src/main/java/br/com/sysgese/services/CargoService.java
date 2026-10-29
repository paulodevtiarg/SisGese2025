package br.com.sysgese.services;

import br.com.sysgese.dtos.CargoDTO;
import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.specifications.CargoSpecification;
import br.com.sysgese.specifications.InternacaoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Cargo;
import br.com.sysgese.repository.CargoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public Page<Cargo> buscar( String descricao, Integer[] status, Pageable pageable){
		
		return cargoRepository.findByDescricaoContainingIgnoreCaseAndStatusIn(descricao, status, pageable);
		
	}

	public Page<Cargo> buscarComFiltro(
			CargoDTO filtro,
			Pageable pageable) {

		Specification<Cargo> spec = Specification.allOf();

// 🔥 Filtro de descrição
		if (filtro.getFiltroDescricao() != null) {
			spec = spec.and(CargoSpecification.descricao(filtro.getFiltroDescricao()));
		}

// 🔥 Filtro de status (se o seu filtroStatus for String, você precisa converter para Integer)
		if (filtro.getFiltroStatus() != null && !filtro.getFiltroStatus().isBlank()) {
			// Se no HTML o status for "1" ou "0"
			Integer statusInt = Integer.valueOf(filtro.getFiltroStatus());
			spec = spec.and(CargoSpecification.status(statusInt));
		}

		return cargoRepository.findAll(spec, pageable);
	}
	@Transactional
	public Cargo salvar(CargoDTO dto) {
		Cargo entity;
		if (dto.getId() != null) {
			entity = cargoRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Cargo não encontrado."));
			// Atualiza
			entity.setDescricao(dto.getDescricao());
			entity.setStatus(dto.getStatus());
		} else {
			// Novo
			entity = new Cargo();
			entity.setDescricao(dto.getDescricao());
			entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 1); // Ativo por padrão
		}
		return cargoRepository.save(entity);
	}

}

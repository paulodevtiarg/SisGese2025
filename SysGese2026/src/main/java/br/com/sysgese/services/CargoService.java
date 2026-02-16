package br.com.sysgese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Cargo;
import br.com.sysgese.repository.CargoRepository;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public Page<Cargo> buscar( String descricao, Integer[] status, Pageable pageable){
		
		return cargoRepository.findByDescricaoContainingIgnoreCaseAndStatusIn(descricao, status, pageable);
		
	}

}

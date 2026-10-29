package br.com.sysgese.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysgese.models.Cargo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CargoRepository extends JpaRepository<Cargo, Long>, JpaSpecificationExecutor<Cargo> {
	// Busca com Funcao por nome e status
    Page<Cargo> findByDescricaoContainingIgnoreCaseAndStatusIn(
            String descricao,
            Integer[] status,  // podemos passar [1] para ativos, [0] para inativos, [0,1] para todos
            Pageable pageable
    );
}

package br.com.sysgese.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.models.Lotacao;

public interface LotacaoRepository
extends JpaRepository<Lotacao, Integer>,
        JpaSpecificationExecutor<Lotacao> {
	
	 default Optional<Lotacao> findAtivaByServidorId(Long idServidor) {
	        return findByServidor_IdAndStatus(idServidor, 1);
	    }

	    // Método derivado real que o Spring Data entende
	    Optional<Lotacao> findByServidor_IdAndStatus(Long idServidor, Integer status);

}

package br.com.sysgese.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.models.Lotacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LotacaoRepository
extends JpaRepository<Lotacao, Integer>,
        JpaSpecificationExecutor<Lotacao> {
	
	 default Optional<Lotacao> findAtivaByServidorId(Long idServidor) {
	        return findByServidor_IdAndStatus(idServidor, 1);
	    }

	    // Método derivado real que o Spring Data entende
	    Optional<Lotacao> findByServidor_IdAndStatus(Long idServidor, Integer status);

	// Busca as lotações ativas de um grupo de servidores
	@Query("SELECT l FROM Lotacao l JOIN FETCH l.unidade WHERE l.servidor.id IN :idsServidores AND l.status = 1")
    List<Lotacao> findByServidorIdInAndStatusAtivo(@Param("idsServidores") Set<Long> idsServidores);

}



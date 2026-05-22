package br.com.sysgese.repository;

import br.com.sysgese.models.Infracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InfracaoRepository extends
        JpaRepository<Infracao, Long>,
        JpaSpecificationExecutor<Infracao> {

    List<Infracao> findByAtivoTrueOrderByArtigoAsc();
}

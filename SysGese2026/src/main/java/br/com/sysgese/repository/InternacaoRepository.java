package br.com.sysgese.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.Internacao;

public interface InternacaoRepository
extends JpaRepository<Internacao, Long>,
        JpaSpecificationExecutor<Internacao> {
	
    boolean existsByAdolescenteIdAndStatus(Long adolescenteId, StatusInternacaoEnum status);
    
    long countByStatus(StatusInternacaoEnum status);

    long countByUnidadeIdAndStatus(Long unidadeId, StatusInternacaoEnum status);
    
    List<Internacao> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
    
    List<Internacao> findByUnidadeIdAndDataInicioBetween(
            Long unidadeId,
            LocalDate inicio,
            LocalDate fim
    );
    
    List<Internacao> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);

}

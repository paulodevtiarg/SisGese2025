package br.com.sysgese.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import br.com.sysgese.models.Unidade;

public interface UnidadeRepository  extends JpaRepository<Unidade, Long> {
	Page<Unidade> findByNomeContainingIgnoreCaseAndCidadeContainingIgnoreCaseAndStatusIn(
            String nome,
            String cidade,
            Integer[] status,
            Pageable pageable
    );
	
	List<Unidade> findByStatus(Integer status, Sort sort);
}

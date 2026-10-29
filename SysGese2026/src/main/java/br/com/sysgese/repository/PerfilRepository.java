package br.com.sysgese.repository;

import br.com.sysgese.models.Funcao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysgese.models.Perfil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PerfilRepository extends JpaRepository<Perfil, Long>, JpaSpecificationExecutor<Perfil> {

    // Busca com filtro por nome e status
    Page<Perfil> findByDescricaoContainingIgnoreCaseAndStatusIn(
            String descricao,
            Integer[] status,  // podemos passar [1] para ativos, [0] para inativos, [0,1] para todos
            Pageable pageable
    );
}
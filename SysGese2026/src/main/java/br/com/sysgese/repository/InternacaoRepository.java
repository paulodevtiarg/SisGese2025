package br.com.sysgese.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.models.Internacao;

public interface InternacaoRepository
extends JpaRepository<Internacao, Long>,
        JpaSpecificationExecutor<Internacao> {
}

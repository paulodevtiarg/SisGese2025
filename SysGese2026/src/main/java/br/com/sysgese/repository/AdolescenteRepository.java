package br.com.sysgese.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysgese.models.Adolescente;

public interface AdolescenteRepository extends JpaRepository<Adolescente, Long> {
	/**
     * Busca paginada de adolescentes com filtros por:
     * nome, apelido, cidade, CPF, faixa de nascimento, status e unidade da internação
     * Mantém o padrão de index do front e já filtra apenas adolescentes da unidade do usuário logado.
     */
	
}

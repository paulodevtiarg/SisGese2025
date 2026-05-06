package br.com.sysgese.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.models.Adolescente;

public interface AdolescenteRepository extends 
JpaRepository<Adolescente, Long>, 
JpaSpecificationExecutor<Adolescente> {
	
	  Optional<Adolescente> findByCpf(String cpf);
	  
	  Long countByIdUnidadeCadastro(Long unidadeId);
	  
	 
	
}
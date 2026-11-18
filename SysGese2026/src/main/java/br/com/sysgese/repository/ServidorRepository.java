package br.com.sysgese.repository;

import java.util.Optional;

import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServidorRepository extends JpaRepository<Servidor, Long>, JpaSpecificationExecutor<Servidor> {


	Optional<Servidor> findByLoginOrCpfOrMatricula(
            String login,
            String cpf,
            String matricula);

	Optional<Servidor> findByLogin(String login);
	Optional<Servidor> findByCpf(String cpf);
	Optional<Servidor> findByMatricula(String matricula);
}

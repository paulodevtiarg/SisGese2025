package br.com.sysgese.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sysgese.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // Buscar endereço ativo do adolescente
    Optional<Endereco> findByAdolescenteIdAndAtivoTrue(Long idAdolescente);

    // Listar todos os endereços (histórico)
    List<Endereco> findByAdolescenteIdOrderByDataInicioDesc(Long idAdolescente);
}
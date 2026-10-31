package br.com.sysgese.repository;

import br.com.sysgese.models.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {

    // Buscar todos os familiares de um adolescente específico
    List<Familiar> findByAdolescenteId(Long adolescenteId);

    // Buscar apenas os familiares ativos
    List<Familiar> findByAdolescenteIdAndStatusTrue(Long adolescenteId);
}
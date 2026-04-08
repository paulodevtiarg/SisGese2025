package br.com.sysgese.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Cicatriz;

@Repository
public interface CicatrizRepository extends JpaRepository<Cicatriz, Long> {
    List<Cicatriz> findByAdolescenteId(Long adolescenteId);
    
    void deleteByAdolescenteId(Long adolescenteId);
    List<Cicatriz> findByAdolescente(Adolescente adolescente);

    void deleteByAdolescente(Adolescente adolescente);
}
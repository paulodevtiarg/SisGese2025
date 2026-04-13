package br.com.sysgese.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Tatuagem;

@Repository
public interface TatuagemRepository extends JpaRepository<Tatuagem, Long> {
    List<Tatuagem> findByAdolescenteId(Long adolescenteId);
    
    void deleteByAdolescenteId(Long adolescenteId);
    
    
    List<Tatuagem> findByAdolescente(Adolescente adolescente);

    void deleteByAdolescente(Adolescente adolescente);
    
    
}

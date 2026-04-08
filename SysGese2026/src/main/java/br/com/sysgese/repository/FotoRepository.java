package br.com.sysgese.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findByAdolescenteId(Long adolescenteId);
    
    List<Foto> findByAdolescente(Adolescente adolescente);

    void deleteByAdolescente(Adolescente adolescente);
    
    
    
    
}

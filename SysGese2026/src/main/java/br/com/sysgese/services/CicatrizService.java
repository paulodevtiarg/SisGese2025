package br.com.sysgese.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.CicatrizDTO;
import br.com.sysgese.mappers.CicatrizMapper;
import br.com.sysgese.models.Cicatriz;
import br.com.sysgese.repository.CicatrizRepository;
@Service
public class CicatrizService {

    @Autowired
    private CicatrizRepository repository;
    
    @Autowired
    private CicatrizMapper cicatrizMapper;

    public Cicatriz salvar(Cicatriz cicatriz) {
        return repository.save(cicatriz);
    }

    public List<Cicatriz> salvarLista(List<Cicatriz> lista) {
        return repository.saveAll(lista);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
    
    
    public List<CicatrizDTO> listarCicatrizes(Long idAdolescente) {
        return cicatrizMapper.toDTOList(
                repository.findByAdolescenteId(idAdolescente)
        );
    }
}
package br.com.sysgese.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Foto;
import br.com.sysgese.repository.FotoRepository;

@Service
public class FotoService {

    @Autowired
    private FotoRepository repository;

    public Foto salvar(Foto foto) {
        return repository.save(foto);
    }

    public List<Foto> salvarLista(List<Foto> lista) {
        return repository.saveAll(lista);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

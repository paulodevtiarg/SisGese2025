package br.com.sysgese.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Tatuagem;
import br.com.sysgese.repository.TatuagemRepository;
@Service
public class TatuagemService {

    @Autowired
    private TatuagemRepository repository;

    public Tatuagem salvar(Tatuagem tatuagem) {
        return repository.save(tatuagem);
    }

    public List<Tatuagem> salvarLista(List<Tatuagem> lista) {
        return repository.saveAll(lista);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
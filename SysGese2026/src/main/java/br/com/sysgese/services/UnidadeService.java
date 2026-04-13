package br.com.sysgese.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Unidade;
import br.com.sysgese.repository.UnidadeRepository;

@Service
public class UnidadeService {
	@Autowired
    private UnidadeRepository repository;
	public Page<Unidade> buscar(
            String nome,
            String cidade,
            Integer[] status,
            Pageable pageable) {

        if (nome == null) nome = "";
        if (cidade == null) cidade = "";

        return repository
                .findByNomeContainingIgnoreCaseAndCidadeContainingIgnoreCaseAndStatusIn(
                        nome,
                        cidade,
                        status,
                        pageable
                );
    }

    public Unidade salvar(Unidade unidade) {
        return repository.save(unidade);
    }

    public Optional<Unidade> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
    public Map<Long, String> buscarNomesPorIds(Set<Long> ids) {

        return repository.findAllById(ids).stream()
                .collect(Collectors.toMap(
                        Unidade::getId,
                        Unidade::getNome
                ));
    }
    public List<Unidade> listarTodas() {
        return repository.findAll(Sort.by("nome"));
    }
    
    public List<Unidade> listarTodasAtivas() {
        return repository.findByStatus(1, Sort.by("nome"));
    }
}

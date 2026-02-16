package br.com.sysgese.services;

import java.util.Optional;

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
}

package br.com.sysgese.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Perfil;
import br.com.sysgese.repository.PerfilRepository;

@Service
public class PerfilService {
	@Autowired
    private PerfilRepository perfilRepository;

    public Page<Perfil> buscar(String descricao, Integer[] status, Pageable pageable) {
        return perfilRepository.findByDescricaoContainingIgnoreCaseAndStatusIn(descricao, status, pageable);
    }
}

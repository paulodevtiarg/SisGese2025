package br.com.sysgese.services;


import br.com.sysgese.dtos.PerfilDTO;
import br.com.sysgese.specifications.PerfilSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Perfil> buscarComFiltro(
            PerfilDTO filtro,
            Pageable pageable) {

        Specification<Perfil> spec = Specification.allOf();

// 🔥 Filtro de descrição
        if (filtro.getFiltroDescricao() != null) {
            spec = spec.and(PerfilSpecification.descricao(filtro.getFiltroDescricao()));
        }

// 🔥 Filtro de status (se o seu filtroStatus for String, você precisa converter para Integer)
        if (filtro.getFiltroStatus() != null && !filtro.getFiltroStatus().isBlank()) {
            // Se no HTML o status for "1" ou "0"
            Integer statusInt = Integer.valueOf(filtro.getFiltroStatus());
            spec = spec.and(PerfilSpecification.status(statusInt));
        }

        return perfilRepository.findAll(spec, pageable);
    }

}

package br.com.sysgese.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sysgese.models.Lotacao;
import br.com.sysgese.repository.LotacaoRepository;
import br.com.sysgese.specifications.LotacaoSpecification;

@Service
public class LotacaoService {

    @Autowired
    private LotacaoRepository repository;

    
 // ==========================
    // Busca lotação ativa de um servidor
    // ==========================
    public Optional<Lotacao> findAtivaByServidorId(Long idServidor) {
        return repository.findAll((root, query, cb) -> cb.and(
                cb.equal(root.get("servidor").get("id"), idServidor),
                cb.equal(root.get("status"), 1),
                cb.or(
                    cb.isNull(root.get("dataSaida")),
                    cb.greaterThan(root.get("dataSaida"), java.time.LocalDate.now())
                )
        )).stream()
          .sorted((l1, l2) -> l2.getDataInicio().compareTo(l1.getDataInicio())) // mais recente primeiro
          .findFirst();
    }
    
    public Page<Lotacao> buscar(
            String servidor,
            String unidade,
            String cargo,
            String funcao,
            Integer[] status,
            Pageable pageable) {

        Specification<Lotacao> spec = Specification
                .where(LotacaoSpecification.servidorNome(servidor))
                .and(LotacaoSpecification.unidadeNome(unidade))
                .and(LotacaoSpecification.cargoNome(cargo))
                .and(LotacaoSpecification.funcaoNome(funcao))
                .and(LotacaoSpecification.statusIn(status));

        return repository.findAll(spec, pageable);
    }
}


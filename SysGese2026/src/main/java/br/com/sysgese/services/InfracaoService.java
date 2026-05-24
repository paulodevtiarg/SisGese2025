package br.com.sysgese.services;

import br.com.sysgese.dtos.InfracaoDTO;
import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.mappers.InfracaoMapper;
import br.com.sysgese.models.Infracao;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.repository.InfracaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfracaoService {
    private final InfracaoRepository repository;
    private final InfracaoMapper mapper;

    public InfracaoService(
            InfracaoRepository repository,
            InfracaoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Lista todas as infrações ativas
     */
    public List<InfracaoDTO> listarAtivas() {

        List<Infracao> infracoes = repository.findByAtivoTrueOrderByArtigoAsc();

        return mapper.toDTOList(infracoes);
    }


}

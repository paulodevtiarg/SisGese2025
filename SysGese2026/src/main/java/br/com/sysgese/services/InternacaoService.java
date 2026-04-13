package br.com.sysgese.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.mappers.InternacaoMapper;
import br.com.sysgese.models.Internacao;
import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.specifications.InternacaoSpecification;

import org.springframework.data.jpa.domain.Specification;

@Service
public class InternacaoService {

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private InternacaoMapper mapper;

    @Autowired
    private LotacaoService lotacaoService;

    /**
     * Busca internações **da unidade do servidor logado**, aplicando filtros
     */
    public Page<InternacaoDTO> buscarInternacoes(
            Servidor servidorLogado,
            String nome,
            String apelido,
            String cidade,
            String cpf,
            Integer idadeMin,
            Integer idadeMax,
            Integer[] status,
            Pageable pageable
    ) {

        LocalDate hoje = LocalDate.now();

        LocalDate dataInicioNascimento = LocalDate.of(1900, 1, 1);
        LocalDate dataFimNascimento = hoje;

        if (idadeMin != null) dataFimNascimento = hoje.minusYears(idadeMin);
        if (idadeMax != null) dataInicioNascimento = hoje.minusYears(idadeMax + 1).plusDays(1);

        Integer[] filtroStatus = status != null ? status : new Integer[]{1};

        // 🔐 unidade do servidor
        Lotacao lotacao = lotacaoService.findAtivaByServidorId(servidorLogado.getId())
                .orElseThrow(() -> new RuntimeException("Servidor não possui lotação ativa"));

        Long unidadeId = lotacao.getUnidade().getId().longValue();

        // 🔥 monta filtros dinâmicos
        Specification<Internacao> spec =
                Specification.where(InternacaoSpecification.unidadeId(unidadeId))
                        .and(InternacaoSpecification.statusInternacaoAtiva()) // só internos ativos
                        .and(InternacaoSpecification.nome(nome))
                        .and(InternacaoSpecification.apelido(apelido))
                        .and(InternacaoSpecification.cidade(cidade))
                        .and(InternacaoSpecification.cpf(cpf))
                        .and(InternacaoSpecification.nascimentoEntre(dataInicioNascimento, dataFimNascimento))
                        .and(InternacaoSpecification.statusCadastro(filtroStatus));

        Page<Internacao> internacoes = internacaoRepository.findAll(spec, pageable);

        return internacoes.map(mapper::toDTO);
    }
}
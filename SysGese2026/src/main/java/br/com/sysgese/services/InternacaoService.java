package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.InternacaoDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.mappers.InternacaoMapper;
import br.com.sysgese.models.Internacao;

import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.specifications.InternacaoSpecification;

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
   
    public Long contarInternadosGeral() {
        return internacaoRepository.countByStatus(StatusInternacaoEnum.ATIVA);
    }

    public Long contarInternadosPorUnidade(Long unidadeId) {
        return internacaoRepository
                .countByUnidadeIdAndStatus(unidadeId, StatusInternacaoEnum.ATIVA);
    }
    
    //para o grafico:
    public Map<Integer, Long> buscarInternacoesPorMes(int ano) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Internacao> lista =
                internacaoRepository.findByDataInicioBetween(inicio, fim);

        Map<Integer, Long> mapa = new HashMap<>();

        // inicializa todos os meses com 0
        for (int i = 1; i <= 12; i++) {
            mapa.put(i, 0L);
        }

        for (Internacao i : lista) {
            int mes = i.getDataInicio().getMonthValue();
            mapa.put(mes, mapa.get(mes) + 1);
        }

        return mapa;
    }
    
    public Map<Integer, Long> buscarInternacoesPorMesPorUnidade(Long unidadeId, int ano) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Internacao> lista =
                internacaoRepository
                    .findByUnidadeIdAndDataInicioBetween(unidadeId, inicio, fim);

        Map<Integer, Long> mapa = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            mapa.put(i, 0L);
        }

        for (Internacao i : lista) {
            int mes = i.getDataInicio().getMonthValue();
            mapa.put(mes, mapa.get(mes) + 1);
        }

        return mapa;
    }
    public Map<String, Long> buscarInternacoesPorUnidade(int ano) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Internacao> lista =
                internacaoRepository.findByDataInicioBetween(inicio, fim);

        Map<String, Long> mapa = new LinkedHashMap<>();

        lista.forEach(i -> {
            String nome = i.getUnidade().getNome();
            mapa.merge(nome, 1L, Long::sum);
        });

        return mapa;
    }
    
    public Page<Internacao> buscarComFiltro(
            InternacaoDTO filtro,
            Long unidadeSession,
            boolean isMaster,
            Pageable pageable) {

    	Specification<Internacao> spec = Specification.allOf();

        // 🔒 REGRA DE UNIDADE
        if (isMaster) {
            // MASTER pode escolher unidade
            if (filtro.getFiltroUnidadeId() != null) {
                spec = spec.and(InternacaoSpecification.unidade(filtro.getFiltroUnidadeId()));
            }
        } else {
            // USUÁRIO NORMAL → preso na própria unidade
            spec = spec.and(InternacaoSpecification.unidade(unidadeSession));
        }

        // outros filtros
        spec = spec.and(InternacaoSpecification.nomeAdolescente(filtro.getNomeAdolescente()));
        spec = spec.and(InternacaoSpecification.cidadeAdolescente(filtro.getCidadeAdolescente()));

        if (filtro.getTipoMedida() != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("tipoMedida"), filtro.getTipoMedida())
            );
        }

        // padrão = ATIVA
        if (filtro.getStatus() == null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("status"), StatusInternacaoEnum.ATIVA)
            );
        } else {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("status"), filtro.getStatus())
            );
        }

        return internacaoRepository.findAll(spec, pageable);
    }
    
}
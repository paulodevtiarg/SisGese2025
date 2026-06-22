package br.com.sysgese.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternacaoService {

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private InternacaoMapper mapper;


    @Autowired
    private LotacaoService lotacaoService;


    @Transactional
    public Internacao salvar(InternacaoDTO dto) {
        Internacao entity;
        if (dto.getId() != null) {
            //Busca
            entity = internacaoRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Registro de Internação não encontrado"));
            mapper.updateEntityFromDTO(dto, entity);
        } else {
            entity = mapper.toEntity(dto);
            entity.setDataCad(LocalDate.now());
        }
        entity.setDataAlt(LocalDate.now());
        return internacaoRepository.save(entity);
    }

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
        if (filtro.getFiltroStatus() == null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), StatusInternacaoEnum.ATIVA)
            );
        } else if (!filtro.getFiltroStatus().equals("TODAS")) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), filtro.getFiltroStatus())
            );
        }

        return internacaoRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> buscarInternacoesPorCidade(Long unidadeId) {

        List<Internacao> internacoes = (unidadeId != null)
                ? internacaoRepository.findByUnidadeIdAndStatus(unidadeId, StatusInternacaoEnum.ATIVA)
                : internacaoRepository.findByStatus(StatusInternacaoEnum.ATIVA);

        Map<String, Long> cidades = new HashMap<>();

        for (Internacao internacao : internacoes) {

            Adolescente adolescente = internacao.getAdolescente();

            if (adolescente == null || adolescente.getEnderecos() == null) {
                continue;
            }

            Endereco enderecoAtivo = adolescente.getEnderecos()
                    .stream()
                    .filter(e -> Boolean.TRUE.equals(e.getAtivo()))
                    .findFirst()
                    .orElse(null);

            if (enderecoAtivo == null) {
                continue;
            }

            String cidade = enderecoAtivo.getCidade();

            if (cidade == null || cidade.isBlank()) {
                cidade = "Não informado";
            }
            cidade = cidade.trim().toUpperCase();


            cidades.put(cidade, cidades.getOrDefault(cidade, 0L) + 1);
        }

        return cidades.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
    public Map<String, Long> buscarInternacoesPorFaixaEtaria(Long unidadeId) {
        List<Internacao> internacoes = internacaoRepository.findAll();
        Map<String, Long> mapa = new LinkedHashMap();
        mapa.put("12-14 anos", 0L);
        mapa.put("15-16 anos", 0L);
        mapa.put("17-18 anos", 0L);
        for (Internacao internacao : internacoes) {
            // filtra por unidade se necessário
            if (unidadeId != null &&
                    !internacao.getUnidade().getId().equals(unidadeId)) {
                continue;
            }
            Adolescente adolescente = internacao.getAdolescente();

            if (adolescente == null) {
                continue;
            }
            // AQUI entra o campo de nascimento
            int idade = Period.between(
                    adolescente.getDataNascimento(),
                    LocalDate.now()
            ).getYears();
            if (idade >= 12 && idade <= 14) {
                mapa.put("12-14 anos", mapa.get("12-14 anos") + 1);
            } else if (idade >= 15 && idade <= 16) {
                mapa.put("15-16 anos", mapa.get("15-16 anos") + 1);
            } else if (idade >= 17 && idade <= 18) {
                mapa.put("17-18 anos", mapa.get("17-18 anos") + 1);
            }

        }

        return mapa;
    }
}

    

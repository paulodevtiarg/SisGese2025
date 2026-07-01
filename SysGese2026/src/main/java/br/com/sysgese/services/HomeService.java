package br.com.sysgese.services;

import br.com.sysgese.dtos.RelatorioDashboardDTO;
import br.com.sysgese.dtos.RelatorioDashboardFiltroDTO;
import br.com.sysgese.models.Unidade;
import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.repository.UnidadeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import br.com.sysgese.repository.AdolescenteRepository;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeService {
    @Autowired
    private  AdolescenteRepository adolescenteRepository;

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private  UnidadeService unidadeService;

    @Autowired
    private JsonMapper jsonMapper; // Injete o JsonMapper
    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private UsuarioLogadoService usuarioService;

    public RelatorioDashboardDTO gerarRelatorio(
            RelatorioDashboardFiltroDTO filtro,
            Long unidadeId) {

        RelatorioDashboardDTO dto = new RelatorioDashboardDTO();

        Integer ano = resolverAno(filtro);

        dto.setModoVisualizacao(
                filtro.getModoVisualizacao() != null
                        ? filtro.getModoVisualizacao()
                        : "TODAS"
        );

        Unidade unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        dto.setNomeUnidade(unidade.getNome());
        dto.setCidade(unidade.getCidade());
        dto.setCnpj(unidade.getCnpj());
        dto.setTelefone(unidade.getTelefone());
        dto.setLogoUrl(unidade.getLogoTimbrado());

        // ✅ SOMENTE INTERNACOES (RELATÓRIO)
        dto.setInternadosUnidade(buscarInternadosUnidade(ano, filtro, unidadeId));
        dto.setInternadosGeral(buscarInternadosGeral(ano, filtro));

        dto.setCidadeUnidade(buscarCidadeUnidade(ano, filtro, unidadeId));
        dto.setCidadeGeral(buscarCidadeGeral(ano, filtro));

        return dto;
    }
    private Integer resolverAno(RelatorioDashboardFiltroDTO filtro) {

        if ("ANO_CORRENTE".equals(filtro.getTipoPeriodo())) {
            return Year.now().getValue();
        }

        if ("ANO_ESPECIFICO".equals(filtro.getTipoPeriodo())) {
            return filtro.getAno();
        }

        if ("INTERVALO".equals(filtro.getTipoPeriodo())) {
            return filtro.getAnoInicio(); // depois adaptamos pra range
        }

        return Year.now().getValue();
    }

    private Long buscarTotalAdolescentesUnidade(Integer ano,
                                                RelatorioDashboardFiltroDTO filtro) {
        return adolescenteRepository.count();
    }
    private Long buscarTotalAdolescentesGeral(Integer ano,
                                              RelatorioDashboardFiltroDTO filtro) {
        return adolescenteRepository.count();
    }

    private Long buscarInternadosUnidade(Integer ano,
                                         RelatorioDashboardFiltroDTO filtro,
                                         Long unidadeId) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        if (filtro.getCidade() != null && !filtro.getCidade().isBlank()) {
            return internacaoRepository.countByUnidadeIdAndCidade(
                    unidadeId,
                    filtro.getCidade(),
                    inicio,
                    fim
            );
        }

        return (long) internacaoRepository
                .findByUnidadeIdAndDataInicioBetween(unidadeId, inicio, fim)
                .size();
    }

    private Long buscarInternadosGeral(Integer ano,
                                       RelatorioDashboardFiltroDTO filtro) {
        System.out.println("CIDADE FILTRO: " + filtro.getCidade());
        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        if (filtro.getCidade() != null && !filtro.getCidade().isBlank()) {
            return internacaoRepository.countGeralByCidade(
                    filtro.getCidade(),
                    inicio,
                    fim
            );
        }

        return (long) internacaoRepository
                .findByDataInicioBetween(inicio, fim)
                .size();
    }

    private String buscarInternacoesUnidadeJson(Integer ano, RelatorioDashboardFiltroDTO filtro, Long unidadeId) {


        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Object[]> dados =
                internacaoRepository.countInternacoesPorMesUnidade(unidadeId, inicio, fim);

        List<Integer> meses = new ArrayList<>();
        List<Long> valores = new ArrayList<>();

        for (Object[] obj : dados) {
            meses.add((Integer) obj[0]);
            valores.add((Long) obj[1]);
        }

        return new Gson().toJson(valores);
    }
    private String buscarInternacoesGeralJson(Integer ano, RelatorioDashboardFiltroDTO filtro) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Object[]> dados =
                internacaoRepository.countInternacoesPorMesGeral(inicio, fim);

        List<Long> valores = new ArrayList<>();

        for (Object[] obj : dados) {
            valores.add((Long) obj[1]);
        }

        return new Gson().toJson(valores);
    }

    private String buscarCidadeUnidadeJson(Integer ano, RelatorioDashboardFiltroDTO filtro, Long unidadeId) {


        List<Object[]> dados =
                internacaoRepository.countInternacoesPorCidadeUnidade(unidadeId);

        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] obj : dados) {
            mapa.put((String) obj[0], (Long) obj[1]);
        }

        return new Gson().toJson(mapa.values());
    }
    /*
    private String buscarCidadeGeralJson(Integer ano, RelatorioDashboardFiltroDTO filtro) {

        List<Object[]> dados =
                internacaoRepository.countInternacoesPorCidadeGeral();

        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] obj : dados) {
            mapa.put((String) obj[0], (Long) obj[1]);
        }

        return new Gson().toJson(mapa.values());
    }*/

    private String buscarInternacoesPorUnidadeJson(Integer ano,
                                                   RelatorioDashboardFiltroDTO filtro) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Object[]> dados =
                internacaoRepository.countInternacoesPorUnidade(inicio, fim);

        List<String> labels = new ArrayList<>();
        List<Long> valores = new ArrayList<>();

        for (Object[] obj : dados) {
            labels.add((String) obj[0]); // nome da unidade
            valores.add((Long) obj[1]);   // total
        }

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("labels", labels);
        json.put("values", valores);

        return jsonMapper.writeValueAsString(json);
    }

    private Map<String, Long> buscarCidadeUnidade(Integer ano,
                                                  RelatorioDashboardFiltroDTO filtro,
                                                  Long unidadeId) {

        List<Object[]> dados;

        if (filtro.getCidade() != null && !filtro.getCidade().isBlank()) {
            dados = internacaoRepository
                    .countInternacoesPorCidadeUnidadeFiltrado(
                            unidadeId,
                            filtro.getCidade()
                    );
        } else {
            dados = internacaoRepository
                    .countInternacoesPorCidadeUnidade(unidadeId);
        }

        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] obj : dados) {
            mapa.put((String) obj[0], (Long) obj[1]);
        }

        return mapa;
    }

    private Map<String, Long> buscarCidadeGeral(Integer ano,
                                                RelatorioDashboardFiltroDTO filtro) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Object[]> dados;

        // 🔥 AQUI ESTÁ A CHAMADA AO REPOSITORY FILTRADO
        if (filtro.getCidade() != null && !filtro.getCidade().isBlank()) {
            dados = internacaoRepository.countInternacoesPorCidadeGeralFiltrado(
                    filtro.getCidade(),
                    inicio,
                    fim
            );
        } else {
            dados = internacaoRepository.countInternacoesPorCidadeGeral(
                    inicio,
                    fim
            );
        }

        Map<String, Long> mapa = new LinkedHashMap<>();

        for (Object[] obj : dados) {
            mapa.put((String) obj[0], (Long) obj[1]);
        }

        return mapa;
    }

}

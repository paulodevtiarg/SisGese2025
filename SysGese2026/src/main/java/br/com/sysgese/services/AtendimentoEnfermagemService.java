package br.com.sysgese.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.sysgese.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO;
import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.mappers.AtendimentoEnfermagemMapper;
import br.com.sysgese.repository.AtendimentoEnfermagemRepository;
import br.com.sysgese.repository.InternacaoRepository;
import br.com.sysgese.specifications.AtendimentoEnfermagemSpecification;

@Service
public class AtendimentoEnfermagemService {

    @Autowired
    private AtendimentoEnfermagemRepository atendimentoRepository;

    @Autowired
    private AtendimentoEnfermagemMapper mapper;

    @Autowired
    private InternacaoRepository internacaoRepository;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private ServidorService servidorService;

    // ==========================================================
    // SALVAR
    // ==========================================================

    @Transactional
    public AtendimentoEnfermagem salvar(
            AtendimentoEnfermagemDTO dto,
            Long unidadeId,
            Long idServidorLogado) {

        Servidor servidor = servidorService.buscarPorId(idServidorLogado)
                .orElseThrow(() -> new RuntimeException("Servidor não encontrado."));

        Unidade unidade = unidadeService.buscarPorId(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada."));

        // ======================================================
        // NOVO ATENDIMENTO
        // ======================================================
        if (dto.getId() == null) {

            List<Internacao> internacoes = internacaoRepository.findByUnidadeIdAndStatus(
                    unidadeId, StatusInternacaoEnum.ATIVA
            );
            boolean adolescenteInternado = internacoes.stream()
                    .anyMatch(i -> i.getAdolescente() != null && i.getAdolescente().getId().equals(dto.getIdAdolescente()));

            if (!adolescenteInternado) {
                throw new RuntimeException("O adolescente não está internado nesta unidade.");
            }

            AtendimentoEnfermagem entity = mapper.toEntity(dto);
            entity.setUnidade(unidade);
            entity.setServidor(servidor);

            return atendimentoRepository.save(entity);
        }

        // ======================================================
        // ALTERAÇÃO (FINALIZAR CONDUTA)
        // ======================================================
        AtendimentoEnfermagem entity = atendimentoRepository
                .findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado."));

        // Segurança
        if (entity.getUnidade() != null && !entity.getUnidade().getId().equals(unidadeId)) {
            throw new RuntimeException("Você não possui permissão para alterar este atendimento.");
        }

        // 🔥 AQUI ESTÁ A CHAVE: No modoConduta, o DTO só tem conduta, encerramento e observações.
        // Nós atualizamos APENAS esses campos, sem tocar nos dados clínicos.

        // Conduta
        entity.setConduta(dto.getConduta());
        entity.setCondutaOutros(dto.getCondutaOutros());

        // Encaminhamento
        entity.setTipoEncaminhamento(dto.getTipoEncaminhamento());
        entity.setEncaminhamentoInterno(dto.getEncaminhamentoInterno());
        entity.setEncaminhamentoExterno(dto.getEncaminhamentoExterno());
        entity.setEncaminhamentoOutros(dto.getEncaminhamentoOutros());

        // Encerramento
        entity.setEncerramento(dto.getEncerramento());
        entity.setObservacoes(dto.getObservacoes());

        // 🔥 Atualiza a lista de prescrições APENAS se o DTO trouxer alguma coisa.
        // Se o DTO vier com a lista vazia (modoConduta sem mexer), mantemos a lista antiga.
        if (dto.getPrescricoes() != null && !dto.getPrescricoes().isEmpty()) {
            // O Mapper tem a lógica de recriar a lista. Mas como estamos no modoConduta,
            // o usuário pode ter adicionado medicamentos novos.
            // Para simplificar, chamamos o mapper para a lista
            List<PrescricaoMedicamento> novasPrescricoes = mapper.toEntityList(dto.getPrescricoes());
            novasPrescricoes.forEach(p -> p.setAtendimento(entity));

            entity.getPrescricoes().clear();
            entity.getPrescricoes().addAll(novasPrescricoes);
        }

        return atendimentoRepository.save(entity);
    }

    // ==========================================================
    // BUSCAR ATENDIMENTOS PENDENTES (Para o Modal do Index)
    // ==========================================================

    @Transactional(readOnly = true)
    public List<AtendimentoEnfermagemDTO> buscarPendentes(
            Long adolescenteId,
            Long unidadeSession,
            boolean isMaster) {

        List<AtendimentoEnfermagem> pendentes = atendimentoRepository.buscarAtendimentosPendentes(
                adolescenteId,
                unidadeSession,
                isMaster
        );

        return mapper.toDTOList(pendentes);
    }


    // ==========================================================
    // BUSCAR POR ID
    // ==========================================================

    @Transactional(readOnly = true)
    public AtendimentoEnfermagemDTO buscarPorId(Long id) {

        AtendimentoEnfermagem entity =
                atendimentoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Atendimento de enfermagem não encontrado.")
                        );

        return mapper.toDTO(entity);
    }


    // ==========================================================
    // LISTAR ATENDIMENTOS DO ADOLESCENTE
    // ==========================================================

    @Transactional(readOnly = true)
    public List<AtendimentoEnfermagem> buscarPorAdolescente(
            Long adolescenteId) {

        return atendimentoRepository
                .findByAdolescenteIdOrderByDataDescHoraDesc(
                        adolescenteId
                );
    }


    // ==========================================================
    // LISTAR ATENDIMENTOS DA UNIDADE
    // ==========================================================

    @Transactional(readOnly = true)
    public List<AtendimentoEnfermagem> buscarPorUnidade(
            Long unidadeId) {

        return atendimentoRepository
                .findByUnidadeIdOrderByDataDescHoraDesc(
                        unidadeId
                );
    }


    // ==========================================================
    // BUSCAR COM FILTROS
    // ==========================================================

    public Page<AtendimentoEnfermagem> buscarComFiltro(
            AtendimentoEnfermagemDTO filtro,
            Long unidadeSession,
            boolean isMaster,
            Pageable pageable) {

        Specification<AtendimentoEnfermagem> spec =
                Specification.allOf();

        // ======================================================
        // REGRA DE UNIDADE
        // ======================================================
        if (isMaster) {
            if (filtro.getFiltroUnidadeId() != null) {
                spec = spec.and(AtendimentoEnfermagemSpecification.unidade(filtro.getFiltroUnidadeId()));
            }
        } else {
            spec = spec.and(AtendimentoEnfermagemSpecification.unidade(unidadeSession));
        }

        // ======================================================
        // NOME DO ADOLESCENTE
        // ======================================================
        if (filtro.getFiltroNomeAdolescente() != null
                && !filtro.getFiltroNomeAdolescente().isBlank()) {
            spec = spec.and(AtendimentoEnfermagemSpecification.nomeAdolescente(filtro.getFiltroNomeAdolescente()));
        }

        // ======================================================
        // CIDADE DO ADOLESCENTE
        // ======================================================
        if (filtro.getFiltroCidadeAdolescente() != null
                && !filtro.getFiltroCidadeAdolescente().isBlank()) {
            spec = spec.and(AtendimentoEnfermagemSpecification.cidadeAdolescente(filtro.getFiltroCidadeAdolescente()));
        }

        return atendimentoRepository.findAll(spec, pageable);
    }


    // ==========================================================
    // ADOLESCENTES DISPONÍVEIS PARA NOVO ATENDIMENTO
    // ==========================================================

    @Transactional(readOnly = true)
    public List<Internacao> listarAdolescentesDisponiveis(
            Long unidadeSession) {

        if (unidadeSession == null) {
            throw new RuntimeException("Unidade do usuário não identificada.");
        }

        return internacaoRepository
                .findByUnidadeIdAndStatus(
                        unidadeSession,
                        StatusInternacaoEnum.ATIVA
                );
    }


    // ==========================================================
    // VALIDAR SE ADOLESCENTE PODE SER ATENDIDO
    // ==========================================================

    @Transactional(readOnly = true)
    public boolean podeAtender(
            Long adolescenteId,
            Long unidadeSession) {

        if (adolescenteId == null || unidadeSession == null) {
            return false;
        }

        List<Internacao> internacoes =
                internacaoRepository
                        .findByUnidadeIdAndStatus(
                                unidadeSession,
                                StatusInternacaoEnum.ATIVA
                        );

        return internacoes.stream()
                .anyMatch(internacao ->
                        internacao.getAdolescente() != null
                                &&
                                internacao.getAdolescente().getId().equals(adolescenteId)
                );
    }

    @Transactional(readOnly = true)
    public Page<AtendimentoEnfermagemResumoDTO> buscarResumoPorAdolescente(
            AtendimentoEnfermagemDTO filtro,
            Long unidadeSession,
            boolean isMaster,
            int page) {

        int size = filtro.getSize() != null ? filtro.getSize() : 10;
        Pageable pageable = PageRequest.of(page, size);
        Long unidadeId = null;

        // REGRA DE UNIDADE
        if (isMaster) {
            if (filtro.getFiltroUnidadeId() != null) {
                unidadeId = filtro.getFiltroUnidadeId();
            }
        } else {
            unidadeId = unidadeSession;
        }

        // 🔥 SITUAÇÃO DA INTERNAÇÃO (Padrão: ATIVA)
        String situacao = filtro.getFiltroSituacaoInternacao();
        boolean buscaAtiva = situacao == null || situacao.isBlank() || situacao.equalsIgnoreCase("ATIVA");

        // BUSCA AGRUPADA POR ADOLESCENTE
        Page<AtendimentoEnfermagemResumoDTO> pagina = atendimentoRepository.buscarResumoPorAdolescente(
                unidadeId,
                filtro.getFiltroNomeAdolescente(),
                filtro.getFiltroCidadeAdolescente(),
                buscaAtiva,
                StatusInternacaoEnum.ATIVA,
                pageable
        );

        // Pós-processamento: Marca quem tem pendência
        pagina.getContent().forEach(resumo -> {
            List<AtendimentoEnfermagemDTO> pendentes = buscarPendentes(
                    resumo.getAdolescenteId(),
                    unidadeSession,
                    isMaster
            );

            resumo.setQuantidadePendentes(pendentes.size());
            resumo.setTemPendencia(!pendentes.isEmpty());
        });

        return pagina;
    }

    @Transactional(readOnly = true)
    public Page<AtendimentoEnfermagemDTO> buscarHistoricoPorAdolescente(
            Long adolescenteId,
            Long unidadeSession,
            boolean isMaster,
            int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("data").descending().and(Sort.by("hora").descending()));

        Page<AtendimentoEnfermagem> paginaEntity;

        if (isMaster) {
            paginaEntity = atendimentoRepository.findByAdolescenteId(adolescenteId, pageable);
        } else {
            paginaEntity = atendimentoRepository.findByAdolescenteIdAndUnidadeId(adolescenteId, unidadeSession, pageable);
        }

        return paginaEntity.map(mapper::toDTO);
    }

    public Map<Integer, Long> buscarAtendimentosEnfermagemPorMes(Long unidadeId, int ano) {

        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        // 🔥 Agora busca pela DATA do atendimento
        List<AtendimentoEnfermagem> lista = atendimentoRepository.findByUnidadeIdAndDataBetween(
                unidadeId, inicio, fim
        );

        Map<Integer, Long> mapa = new HashMap<>();

        // Inicializa todos os meses com zero
        for (int i = 1; i <= 12; i++) {
            mapa.put(i, 0L);
        }

        // Agrupa pela data
        for (AtendimentoEnfermagem atendimento : lista) {
            if (atendimento.getData() != null) {
                int mes = atendimento.getData().getMonthValue();
                mapa.put(mes, mapa.get(mes) + 1);
            }
        }

        return mapa;
    }

    public Long contarAtendimentosNoAno(Long unidadeId, int ano) {
        // Reaproveita o método que já busca os dados para o gráfico
        Map<Integer, Long> mapa = buscarAtendimentosEnfermagemPorMes(unidadeId, ano);

        // Soma todos os meses
        return mapa.values().stream().mapToLong(Long::longValue).sum();
    }

    public Long contarAtendimentosGeral() {
        return atendimentoRepository.count();
    }

    public Long contarAtendimentosPorUnidade(Long unidadeId) {
        return atendimentoRepository.countByUnidadeId(unidadeId);
    }


}
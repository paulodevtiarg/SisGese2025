package br.com.sysgese.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO;
import br.com.sysgese.dtos.AtendimentoEnfermagemDTO;
import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.mappers.AtendimentoEnfermagemMapper;
import br.com.sysgese.models.AtendimentoEnfermagem;
import br.com.sysgese.models.Internacao;
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


    // ==========================================================
    // SALVAR
    // ==========================================================

    @Transactional
    public AtendimentoEnfermagem salvar(
            AtendimentoEnfermagemDTO dto,
            Long unidadeSession) {

        AtendimentoEnfermagem entity;

        // ======================================================
        // NOVO ATENDIMENTO
        // ======================================================

        if (dto.getId() == null) {

            if (unidadeSession == null) {
                throw new RuntimeException(
                        "Unidade do usuário não identificada."
                );
            }

            // --------------------------------------------------
            // VERIFICA SE O ADOLESCENTE ESTÁ INTERNADO
            // NESTA UNIDADE
            // --------------------------------------------------

            List<Internacao> internacoes =
                    internacaoRepository
                            .findByUnidadeIdAndStatus(
                                    unidadeSession,
                                    StatusInternacaoEnum.ATIVA
                            );

            boolean adolescenteInternado =
                    internacoes.stream()
                            .anyMatch(internacao ->
                                    internacao.getAdolescente() != null
                                            &&
                                            internacao.getAdolescente()
                                                    .getId()
                                                    .equals(dto.getIdAdolescente())
                            );

            if (!adolescenteInternado) {

                throw new RuntimeException(
                        "O adolescente não está internado nesta unidade."
                );
            }

            // --------------------------------------------------
            // Garante que o atendimento pertence à unidade
            // da sessão
            // --------------------------------------------------

            dto.setIdUnidade(unidadeSession);

            // --------------------------------------------------
            // CONVERTE DTO -> ENTITY
            // --------------------------------------------------

            entity = mapper.toEntity(dto);

            // --------------------------------------------------
            // SALVA
            // --------------------------------------------------

            return atendimentoRepository.save(entity);
        }


        // ======================================================
        // ALTERAÇÃO
        // ======================================================

        entity = atendimentoRepository
                .findById(dto.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Atendimento de enfermagem não encontrado."
                        )
                );

        // ------------------------------------------------------
        // SEGURANÇA:
        // usuário normal só pode alterar atendimento
        // da própria unidade
        // ------------------------------------------------------

        if (unidadeSession != null
                && entity.getUnidade() != null
                && !entity.getUnidade()
                .getId()
                .equals(unidadeSession)) {

            throw new RuntimeException(
                    "Você não possui permissão para alterar "
                            + "este atendimento."
            );
        }

        // ------------------------------------------------------
        // Atualização parcial
        // ------------------------------------------------------

        mapper.updateEntityFromDTO(dto, entity);

        return atendimentoRepository.save(entity);
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
                                new RuntimeException(
                                        "Atendimento de enfermagem "
                                                + "não encontrado."
                                )
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

            // MASTER pode escolher a unidade

            if (filtro.getFiltroUnidadeId() != null) {

                spec = spec.and(
                        AtendimentoEnfermagemSpecification
                                .unidade(
                                        filtro.getFiltroUnidadeId()
                                )
                );
            }

        } else {

            // USUÁRIO NORMAL:
            // somente sua própria unidade

            spec = spec.and(
                    AtendimentoEnfermagemSpecification
                            .unidade(unidadeSession)
            );
        }


        // ======================================================
        // NOME DO ADOLESCENTE
        // ======================================================

        if (filtro.getFiltroNomeAdolescente() != null
                && !filtro.getFiltroNomeAdolescente().isBlank()) {

            spec = spec.and(
                    AtendimentoEnfermagemSpecification
                            .nomeAdolescente(
                                    filtro.getFiltroNomeAdolescente()
                            )
            );
        }


        // ======================================================
        // CIDADE DO ADOLESCENTE
        // ======================================================

        if (filtro.getFiltroCidadeAdolescente() != null
                && !filtro.getFiltroCidadeAdolescente().isBlank()) {

            spec = spec.and(
                    AtendimentoEnfermagemSpecification
                            .cidadeAdolescente(
                                    filtro.getFiltroCidadeAdolescente()
                            )
            );
        }


        // ======================================================
        // STATUS
        // ======================================================

        /*
         * O atendimento não possui status próprio.
         *
         * Portanto, não aplicamos filtro de status aqui.
         */


        return atendimentoRepository.findAll(
                spec,
                pageable
        );
    }


    // ==========================================================
    // ADOLESCENTES DISPONÍVEIS PARA NOVO ATENDIMENTO
    // ==========================================================

    @Transactional(readOnly = true)
    public List<Internacao> listarAdolescentesDisponiveis(
            Long unidadeSession) {

        if (unidadeSession == null) {

            throw new RuntimeException(
                    "Unidade do usuário não identificada."
            );
        }

        /*
         * Reutilizamos exatamente o método que
         * já existe no InternacaoRepository.
         *
         * Não alteramos absolutamente nada nele.
         */

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

        if (adolescenteId == null
                || unidadeSession == null) {

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
                                internacao.getAdolescente()
                                        .getId()
                                        .equals(adolescenteId)
                );
    }

    @Transactional(readOnly = true)
    public Page<AtendimentoEnfermagemResumoDTO>
    buscarResumoPorAdolescente(
            AtendimentoEnfermagemDTO filtro,
            Long unidadeSession,
            boolean isMaster,
            int page) {
        int size = filtro.getSize() != null ? filtro.getSize() : 10;
        Pageable pageable =
                PageRequest.of(
                        page,
                        size
                );
        Long unidadeId = null;

        // ======================================================
        // REGRA DE UNIDADE
        // ======================================================

        if (isMaster) {

            // Master só filtra por unidade se tiver escolhido uma
            if (filtro.getFiltroUnidadeId() != null) {
                unidadeId = filtro.getFiltroUnidadeId();
            }

        } else {

            // Usuário normal sempre vê somente sua unidade
            unidadeId = unidadeSession;
        }


        // ======================================================
        // BUSCA AGRUPADA POR ADOLESCENTE
        // ======================================================

        return atendimentoRepository.buscarResumoPorAdolescente(
                unidadeId,
                filtro.getFiltroNomeAdolescente(),
                filtro.getFiltroCidadeAdolescente(),
                pageable
        );
    }
}
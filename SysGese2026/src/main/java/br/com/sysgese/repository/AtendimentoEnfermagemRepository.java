package br.com.sysgese.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.AtendimentoEnfermagem;

public interface AtendimentoEnfermagemRepository
        extends JpaRepository<AtendimentoEnfermagem, Long>,
        JpaSpecificationExecutor<AtendimentoEnfermagem> {


    // ==========================================================
    // ATENDIMENTOS POR ADOLESCENTE
    // ==========================================================

    List<AtendimentoEnfermagem> findByAdolescenteIdOrderByDataDescHoraDesc(
            Long adolescenteId
    );


    @Query("""
    SELECT a FROM AtendimentoEnfermagem a
    WHERE a.adolescente.id = :adolescenteId
    AND a.encerramento IN ('EM_OBSERVACAO', 'NECESSITA_ACOMPANHAMENTO', 'NECESSITA_REAVALIACAO')
    ORDER BY a.data DESC, a.hora DESC
""")
    List<AtendimentoEnfermagem> buscarAtendimentosPendentesPorAdolescente(@Param("adolescenteId") Long adolescenteId);

    // ==========================================================
    // ATENDIMENTOS POR UNIDADE
    // ==========================================================

    List<AtendimentoEnfermagem> findByUnidadeIdOrderByDataDescHoraDesc(
            Long unidadeId
    );
    @Query("""
    SELECT a FROM AtendimentoEnfermagem a
    WHERE a.adolescente.id = :adolescenteId
    AND a.encerramento IN ('EM_OBSERVACAO', 'NECESSITA_ACOMPANHAMENTO', 'NECESSITA_REAVALIACAO')
    AND (:isMaster = true OR a.unidade.id = :unidadeId)
    ORDER BY a.data DESC, a.hora DESC
""")
    List<AtendimentoEnfermagem> buscarAtendimentosPendentes(
            @Param("adolescenteId") Long adolescenteId,
            @Param("unidadeId") Long unidadeId,
            @Param("isMaster") boolean isMaster
    );

    // ==========================================================
    // ATENDIMENTOS POR SERVIDOR
    // ==========================================================

    List<AtendimentoEnfermagem> findByServidorIdOrderByDataDescHoraDesc(
            Long servidorId
    );


    // ==========================================================
    // ATENDIMENTOS POR DATA
    // ==========================================================

    List<AtendimentoEnfermagem> findByDataOrderByHoraDesc(
            LocalDate data
    );


    List<AtendimentoEnfermagem> findByDataBetweenOrderByDataDescHoraDesc(
            LocalDate inicio,
            LocalDate fim
    );


    // ==========================================================
    // ATENDIMENTOS POR UNIDADE E DATA
    // ==========================================================

    List<AtendimentoEnfermagem> findByUnidadeIdAndDataBetweenOrderByDataDescHoraDesc(
            Long unidadeId,
            LocalDate inicio,
            LocalDate fim
    );


    // ==========================================================
    // HISTÓRICO DO ADOLESCENTE EM UMA UNIDADE
    // ==========================================================

    List<AtendimentoEnfermagem> findByAdolescenteIdAndUnidadeIdOrderByDataDescHoraDesc(
            Long adolescenteId,
            Long unidadeId
    );


    // ==========================================================
    // QUANTIDADE DE ATENDIMENTOS
    // ==========================================================

    long countByUnidadeId(Long unidadeId);

    long countByAdolescenteId(Long adolescenteId);

    long countByServidorId(Long servidorId);

    long countByData(LocalDate data);


    // ==========================================================
    // QUANTIDADE POR UNIDADE E PERÍODO
    // ==========================================================

    long countByUnidadeIdAndDataBetween(
            Long unidadeId,
            LocalDate inicio,
            LocalDate fim
    );


    // ==========================================================
    // VERIFICA SE EXISTE ATENDIMENTO
    // ==========================================================

    boolean existsByAdolescenteIdAndData(
            Long adolescenteId,
            LocalDate data
    );


    // ==========================================================
    // BUSCA OS ADOLESCENTES QUE PODEM RECEBER ATENDIMENTO
    //
    // REGRA:
    // - precisa estar internado
    // - precisa estar na unidade informada
    // ==========================================================

    @Query("""
        SELECT DISTINCT i.adolescente
        FROM Internacao i
        JOIN i.adolescente a
        WHERE i.unidade.id = :unidadeId
          AND i.status = :status
        ORDER BY a.nome
    """)
    List<br.com.sysgese.models.Adolescente> listarAdolescentesInternados(
            @Param("unidadeId") Long unidadeId,
            @Param("status") StatusInternacaoEnum status
    );


    // ==========================================================
    // BUSCA ADOLESCENTES INTERNADOS POR NOME
    // ==========================================================

    @Query("""
        SELECT DISTINCT i.adolescente
        FROM Internacao i
        JOIN i.adolescente a
        WHERE i.unidade.id = :unidadeId
          AND i.status = :status
          AND LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
        ORDER BY a.nome
    """)
    List<br.com.sysgese.models.Adolescente> listarAdolescentesInternadosPorNome(
            @Param("unidadeId") Long unidadeId,
            @Param("status") StatusInternacaoEnum status,
            @Param("nome") String nome
    );


    // ==========================================================
    // BUSCA ADOLESCENTES INTERNADOS POR CIDADE
    // ==========================================================

    @Query("""
        SELECT DISTINCT i.adolescente
        FROM Internacao i
        JOIN i.adolescente a
        JOIN a.enderecos e
        WHERE i.unidade.id = :unidadeId
          AND i.status = :status
          AND e.ativo = true
          AND LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))
        ORDER BY a.nome
    """)
    List<br.com.sysgese.models.Adolescente> listarAdolescentesInternadosPorCidade(
            @Param("unidadeId") Long unidadeId,
            @Param("status") StatusInternacaoEnum status,
            @Param("cidade") String cidade
    );


    // ==========================================================
    // BUSCA ADOLESCENTE ESPECÍFICO
    //
    // ESTA É MUITO IMPORTANTE PARA A REGRA DE SEGURANÇA
    // ==========================================================

    @Query("""
        SELECT i.adolescente
        FROM Internacao i
        WHERE i.adolescente.id = :adolescenteId
          AND i.unidade.id = :unidadeId
          AND i.status = :status
    """)
    br.com.sysgese.models.Adolescente buscarAdolescenteInternadoNaUnidade(
            @Param("adolescenteId") Long adolescenteId,
            @Param("unidadeId") Long unidadeId,
            @Param("status") StatusInternacaoEnum status
    );




    Page<AtendimentoEnfermagem> findByAdolescenteId(Long adolescenteId, Pageable pageable);

    // Se for Master (ou não tiver unidade), busca todos. Se não, busca da unidade.
    Page<AtendimentoEnfermagem> findByAdolescenteIdAndUnidadeId(Long adolescenteId, Long unidadeId, Pageable pageable);
    @Query("""
    SELECT ae.adolescente.id, a.nome, a.cidadeNascimento, a.ufNascimento, a.fotoRegistro, a.mae, 
           u.nome, uc.nome, a.dataNascimento, count(ae.id), max(ae.data)
    FROM AtendimentoEnfermagem ae
    JOIN ae.adolescente a
    JOIN a.unidadeCadastro uc
    JOIN ae.unidade u
    WHERE (:unidadeId IS NULL OR ae.unidade.id = :unidadeId)
      AND (:filtroNome IS NULL OR :filtroNome = '' OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :filtroNome, '%')))
      AND (:filtroCidade IS NULL OR :filtroCidade = '' OR EXISTS (
          SELECT 1 FROM Endereco e WHERE e.adolescente = a AND e.ativo = true AND LOWER(e.cidade) LIKE LOWER(CONCAT('%', :filtroCidade, '%'))
      ))
      AND (
          (:buscaAtiva = true AND EXISTS (
              SELECT 1 FROM Internacao i WHERE i.adolescente = a AND i.unidade.id = ae.unidade.id AND i.status = :statusAtiva
          ))
          OR 
          (:buscaAtiva = false AND NOT EXISTS (
              SELECT 1 FROM Internacao i WHERE i.adolescente = a AND i.unidade.id = ae.unidade.id AND i.status = :statusAtiva
          ))
      )
    GROUP BY ae.adolescente.id, a.nome, a.cidadeNascimento, a.ufNascimento, a.fotoRegistro, a.mae, 
             u.nome, uc.nome, a.dataNascimento
    ORDER BY max(ae.data) DESC, max(ae.hora) DESC
""")
    Page<AtendimentoEnfermagemResumoDTO> buscarResumoPorAdolescente(
            @Param("unidadeId") Long unidadeId,
            @Param("filtroNome") String filtroNome,
            @Param("filtroCidade") String filtroCidade,
            @Param("buscaAtiva") boolean buscaAtiva,
            @Param("statusAtiva") StatusInternacaoEnum statusAtiva,
            Pageable pageable
    );
}
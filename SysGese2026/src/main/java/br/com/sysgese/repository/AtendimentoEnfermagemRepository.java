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


    // ==========================================================
    // ATENDIMENTOS POR UNIDADE
    // ==========================================================

    List<AtendimentoEnfermagem> findByUnidadeIdOrderByDataDescHoraDesc(
            Long unidadeId
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


    @Query(
            value = """
            SELECT new br.com.sysgese.dtos.AtendimentoEnfermagemResumoDTO(
                a.adolescente.id,
                a.adolescente.nome,
                a.adolescente.cidadeNascimento,
                a.adolescente.ufNascimento,
                a.adolescente.fotoRegistro,
                a.adolescente.mae,
                a.unidade.nome,
                a.adolescente.unidadeCadastro.nome,
                a.adolescente.dataNascimento,
                COUNT(a.id), max(a.data)
            )
            FROM AtendimentoEnfermagem a
            WHERE
                (:unidadeId IS NULL OR a.unidade.id = :unidadeId)

                AND (
                    :nome IS NULL
                    OR :nome = ''
                    OR LOWER(a.adolescente.nome)
                        LIKE LOWER(CONCAT('%', :nome, '%'))
                )

                AND (
                    :cidade IS NULL
                    OR :cidade = ''
                    OR EXISTS (
                        SELECT e.id
                        FROM Endereco e
                        WHERE e MEMBER OF a.adolescente.enderecos
                          AND e.ativo = true
                          AND LOWER(e.cidade)
                              LIKE LOWER(CONCAT('%', :cidade, '%'))
                    )
                )

            GROUP BY
                a.adolescente.id,
                a.adolescente.nome,
                a.adolescente.cidadeNascimento,
                a.adolescente.ufNascimento,
                a.adolescente.fotoRegistro,
                a.adolescente.mae,
                a.unidade.nome,
                a.adolescente.unidadeCadastro.nome,
                a.adolescente.dataNascimento

            ORDER BY MAX(a.data) DESC, MAX(a.hora) DESC
        """,
            countQuery = """
            SELECT COUNT(DISTINCT a.adolescente.id)
            FROM AtendimentoEnfermagem a
            WHERE
                (:unidadeId IS NULL OR a.unidade.id = :unidadeId)

                AND (
                    :nome IS NULL
                    OR :nome = ''
                    OR LOWER(a.adolescente.nome)
                        LIKE LOWER(CONCAT('%', :nome, '%'))
                )

                AND (
                    :cidade IS NULL
                    OR :cidade = ''
                    OR EXISTS (
                        SELECT e.id
                        FROM Endereco e
                        WHERE e MEMBER OF a.adolescente.enderecos
                          AND e.ativo = true
                          AND LOWER(e.cidade)
                              LIKE LOWER(CONCAT('%', :cidade, '%'))
                    )
                )
        """
    )
    Page<AtendimentoEnfermagemResumoDTO> buscarResumoPorAdolescente(
            @Param("unidadeId") Long unidadeId,
            @Param("nome") String nome,
            @Param("cidade") String cidade,
            Pageable pageable
    );
}
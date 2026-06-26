package br.com.sysgese.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.Internacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InternacaoRepository
extends JpaRepository<Internacao, Long>,
        JpaSpecificationExecutor<Internacao> {
	
    boolean existsByAdolescenteIdAndStatus(Long adolescenteId, StatusInternacaoEnum status);
    
    long countByStatus(StatusInternacaoEnum status);

    long countByUnidadeIdAndStatus(Long unidadeId, StatusInternacaoEnum status);

    List<Internacao> findByAdolescenteIdOrderByDataInicioDesc(Long id);
    
    List<Internacao> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
    
    List<Internacao> findByUnidadeIdAndDataInicioBetween(
            Long unidadeId,
            LocalDate inicio,
            LocalDate fim
    );
    
    List<Internacao> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Internacao> findByAdolescenteIdInAndStatus(
            List<Long> ids,
            StatusInternacaoEnum status
    );

    List<Internacao> findByUnidadeId(Long unidadeId);
    List<Internacao> findByStatus(StatusInternacaoEnum status);
    List<Internacao> findByUnidadeIdAndStatus(Long unidadeId, StatusInternacaoEnum status);


    @Query("""
        SELECT FUNCTION('MONTH', i.dataInicio), COUNT(i)
        FROM Internacao i
        WHERE i.unidade.id = :unidadeId
          AND i.dataInicio BETWEEN :inicio AND :fim
        GROUP BY FUNCTION('MONTH', i.dataInicio)
        ORDER BY FUNCTION('MONTH', i.dataInicio)
    """)
    List<Object[]> countInternacoesPorMesUnidade(
            @Param("unidadeId") Long unidadeId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
        SELECT FUNCTION('MONTH', i.dataInicio), COUNT(i)
        FROM Internacao i
        WHERE i.dataInicio BETWEEN :inicio AND :fim
        GROUP BY FUNCTION('MONTH', i.dataInicio)
        ORDER BY FUNCTION('MONTH', i.dataInicio)
    """)
    List<Object[]> countInternacoesPorMesGeral(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    @Query("""
        SELECT e.cidade, COUNT(DISTINCT i.id)
        FROM Internacao i
        JOIN i.adolescente a
        JOIN a.enderecos e
        WHERE i.unidade.id = :unidadeId
          AND e.ativo = true
        GROUP BY e.cidade
        ORDER BY e.cidade
    """)
    List<Object[]> countInternacoesPorCidadeUnidade(
            @Param("unidadeId") Long unidadeId
    );

    @Query("""
        SELECT e.cidade, COUNT(DISTINCT i.id)
        FROM Internacao i
        JOIN i.adolescente a
        JOIN a.enderecos e
        WHERE e.ativo = true
        GROUP BY e.cidade
        ORDER BY e.cidade
    """)
    List<Object[]> countInternacoesPorCidadeGeral();

    @Query("""
        SELECT i.unidade.nome, COUNT(i)
        FROM Internacao i
        WHERE i.dataInicio BETWEEN :inicio AND :fim
        GROUP BY i.unidade.nome
        ORDER BY i.unidade.nome
    """)
    List<Object[]> countInternacoesPorUnidade(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );


    @Query("""
    SELECT DISTINCT e.cidade
    FROM Internacao i
    JOIN i.adolescente a
    JOIN a.enderecos e
    WHERE e.ativo = true
      AND e.cidade IS NOT NULL
      AND TRIM(e.cidade) <> ''
    ORDER BY e.cidade
""")
    List<String> listarCidadesInternados();



}

package br.com.sysgese.specifications;

import br.com.sysgese.models.AtendimentoEnfermagem;
import br.com.sysgese.models.Adolescente;
import br.com.sysgese.models.Endereco;
import br.com.sysgese.models.Unidade;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

public class AtendimentoEnfermagemSpecification {

    // ==========================================================
    // UNIDADE
    // ==========================================================

    public static Specification<AtendimentoEnfermagem> unidade(
            Long unidadeId) {

        return (root, query, criteriaBuilder) -> {

            if (unidadeId == null) {
                return criteriaBuilder.conjunction();
            }

            Join<AtendimentoEnfermagem, Unidade> unidade =
                    root.join("unidade", JoinType.INNER);

            return criteriaBuilder.equal(
                    unidade.get("id"),
                    unidadeId
            );
        };
    }


    // ==========================================================
    // NOME DO ADOLESCENTE
    // ==========================================================

    public static Specification<AtendimentoEnfermagem> nomeAdolescente(
            String nome) {

        return (root, query, criteriaBuilder) -> {

            if (nome == null || nome.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            Join<AtendimentoEnfermagem, Adolescente> adolescente =
                    root.join("adolescente", JoinType.INNER);

            return criteriaBuilder.like(
                    criteriaBuilder.upper(
                            adolescente.get("nome")
                    ),
                    "%" + nome.trim().toUpperCase() + "%"
            );
        };
    }


    // ==========================================================
    // CIDADE DO ADOLESCENTE
    // ==========================================================

    public static Specification<AtendimentoEnfermagem> cidadeAdolescente(
            String cidade) {

        return (root, query, criteriaBuilder) -> {

            if (cidade == null || cidade.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            Join<AtendimentoEnfermagem, Adolescente> adolescente =
                    root.join("adolescente", JoinType.INNER);

            Join<Adolescente, Endereco> endereco =
                    adolescente.join(
                            "enderecos",
                            JoinType.INNER
                    );

            Predicate cidadePredicate =
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    endereco.get("cidade")
                            ),
                            "%" + cidade.trim().toUpperCase() + "%"
                    );

            /*
             * Consideramos somente o endereço ativo.
             */

            Predicate ativoPredicate =
                    criteriaBuilder.equal(
                            endereco.get("ativo"),
                            true
                    );

            return criteriaBuilder.and(
                    cidadePredicate,
                    ativoPredicate
            );
        };
    }
}
package br.com.sysgese.specifications;

import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class ServidorSpecification {

    // Filtra pela unidade da lotação atual
    public static Specification<Servidor> unidade(Long unidadeId) {
        return (root, query, cb) -> {
            // Se for master e não escolheu unidade, não filtra por unidade específica
            // Mas ainda garante que o servidor tenha uma lotação ativa em algum lugar
            Subquery<Integer> subquery = query.subquery(Integer.class);
            var lotacaoRoot = subquery.from(Lotacao.class);

            subquery.select(lotacaoRoot.get("id"))
                    .where(
                            cb.equal(lotacaoRoot.get("servidor"), root),
                            cb.equal(lotacaoRoot.get("status"), 1)
                    );

            if (unidadeId == null) {
                // Master sem filtro: traz quem tem qualquer lotação ativa
                return cb.exists(subquery);
            } else {
                // Com filtro de unidade: traz quem tem lotação ativa NAQUELA unidade
                subquery.where(cb.equal(lotacaoRoot.get("unidade").get("id"), unidadeId));
                return cb.exists(subquery);
            }
        };
    }

    public static Specification<Servidor> nome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return cb.conjunction();
            return cb.like(cb.upper(root.get("nome")), "%" + nome.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Servidor> matricula(String matricula) {
        return (root, query, cb) -> {
            if (matricula == null || matricula.isBlank()) return cb.conjunction();
            return cb.like(cb.upper(root.get("matricula")), "%" + matricula.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Servidor> cidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null || cidade.isBlank()) return cb.conjunction();
            return cb.like(cb.upper(root.get("cidade")), "%" + cidade.trim().toUpperCase() + "%");
        };
    }

    public static Specification<Servidor> cpf(String cpf) {
        return (root, query, cb) -> {
            if (cpf == null || cpf.isBlank()) return cb.conjunction();
            // Remove a máscara do CPF caso o usuário digite com pontos e traços
            String cpfLimpo = cpf.replaceAll("\\D", "");
            return cb.like(root.get("cpf"), "%" + cpfLimpo + "%");
        };
    }

    public static Specification<Servidor> statusCadastro(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank() || status.equalsIgnoreCase("TODOS")) {
                return cb.conjunction();
            }
            Integer statusInt = status.equalsIgnoreCase("ATIVO") ? 1 : 0;
            return cb.equal(root.get("status"), statusInt);
        };
    }
}
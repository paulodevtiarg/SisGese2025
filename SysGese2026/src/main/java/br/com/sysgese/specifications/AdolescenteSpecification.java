package br.com.sysgese.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.sysgese.models.Adolescente;


public class AdolescenteSpecification {

    public static Specification<Adolescente> unidadeCadastro(Long idUnidade) {
        return (root, query, cb) -> {
            if (idUnidade == null) return null;
            return cb.equal(root.get("idUnidadeCadastro"), idUnidade);
        };
    }

    public static Specification<Adolescente> nome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(
                    cb.upper(root.get("nome")),
                    "%" + nome.toUpperCase() + "%"
            );
        };
    }

    public static Specification<Adolescente> apelido(String apelido) {
        return (root, query, cb) -> {
            if (apelido == null || apelido.isBlank()) return null;
            return cb.like(
                    cb.upper(root.get("apelido")),
                    "%" + apelido.toUpperCase() + "%"
            );
        };
    }

    public static Specification<Adolescente> cidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null || cidade.isBlank()) return null;
            return cb.like(
                    cb.upper(root.get("cidade")),
                    "%" + cidade.toUpperCase() + "%"
            );
        };
    }

    public static Specification<Adolescente> cpf(String cpf) {
        return (root, query, cb) -> {
            if (cpf == null || cpf.isBlank()) return null;
            return cb.equal(root.get("cpf"), cpf);
        };
    }

    public static Specification<Adolescente> nascimentoEntre(LocalDate inicio, LocalDate fim) {
        return (root, query, cb) -> {
            if (inicio == null || fim == null) return null;
            return cb.between(root.get("dataNascimento"), inicio, fim);
        };
    }

    public static Specification<Adolescente> statusCadastro(Integer[] status) {
        return (root, query, cb) -> {
            if (status == null || status.length == 0) return null;
            return root.get("status").in((Object[]) status);
        };
    }
}
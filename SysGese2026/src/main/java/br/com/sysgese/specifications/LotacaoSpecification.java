package br.com.sysgese.specifications;

import org.springframework.data.jpa.domain.Specification;
import br.com.sysgese.models.Lotacao;

public class LotacaoSpecification {

    public static Specification<Lotacao> servidorId(Integer servidorId) {
        return (root, query, cb) ->
                servidorId == null ? null :
                cb.equal(root.get("servidor").get("id"), servidorId);
    }

    public static Specification<Lotacao> unidadeId(Integer unidadeId) {
        return (root, query, cb) ->
                unidadeId == null ? null :
                cb.equal(root.get("unidade").get("id"), unidadeId);
    }

    public static Specification<Lotacao> cargoId(Integer cargoId) {
        return (root, query, cb) ->
                cargoId == null ? null :
                cb.equal(root.get("cargo").get("id"), cargoId);
    }

    public static Specification<Lotacao> funcaoId(Integer funcaoId) {
        return (root, query, cb) ->
                funcaoId == null ? null :
                cb.equal(root.get("funcao").get("id"), funcaoId);
    }

    public static Specification<Lotacao> statusIn(Integer[] status) {
        return (root, query, cb) ->
                status == null ? null :
                root.get("status").in((Object[]) status);
    }
    
    public static Specification<Lotacao> servidorNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(
                cb.lower(root.join("servidor").get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Lotacao> unidadeNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(
                cb.lower(root.join("unidade").get("nome")),
                "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Lotacao> cargoNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(
                cb.lower(root.join("cargo").get("descricao")),
                "%" + nome.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Lotacao> funcaoNome(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(
                cb.lower(root.join("funcao").get("descricao")),
                "%" + nome.toLowerCase() + "%"
            );
        };
    }

    
    
}
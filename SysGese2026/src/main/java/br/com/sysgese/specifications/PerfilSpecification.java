package br.com.sysgese.specifications;

import br.com.sysgese.models.Perfil;
import org.springframework.data.jpa.domain.Specification;

public class PerfilSpecification {
    public static Specification<Perfil> descricao(String descricao) {
        return (root, query, criteriaBuilder) -> {
            // Se o filtro vier vazio, ignora e busca todos
            if (descricao == null || descricao.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            // Busca no campo 'descricao' da entidade Cargo
            return criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("descricao")),
                    "%" + descricao.trim().toUpperCase() + "%"
            );
        };
    }

    // E para o status (que também está no seu DTO como filtroStatus):
    public static Specification<Perfil> status(Integer status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

}

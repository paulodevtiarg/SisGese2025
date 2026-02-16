package br.com.sysgese.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.sysgese.enumerators.StatusInternacaoEnum;
import br.com.sysgese.models.Internacao;

public class InternacaoSpecification {

	public static Specification<Internacao> unidadeId(Long id) {
		 if (id == null) return alwaysTrue();
		
		 return (root, query, cb) ->
         cb.equal(root.get("unidade").get("id"), id);
	}
    
  

    // 🔥 apenas internações ativas
	public static Specification<Internacao> statusInternacaoAtiva() {
	    return (root, query, cb) ->
	            cb.equal(root.get("status"), StatusInternacaoEnum.ATIVA);
	}

    public static Specification<Internacao> nome(String nome) {
    	if (nome == null || nome.isBlank()) return alwaysTrue();

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("adolescente").get("nome")),
                        "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Internacao> apelido(String apelido) {
    	if (apelido == null || apelido.isBlank()) return alwaysTrue();

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("adolescente").get("apelido")),
                        "%" + apelido.toLowerCase() + "%");
    }

    public static Specification<Internacao> cidade(String cidade) {
    	 if (cidade == null || cidade.isBlank()) return alwaysTrue();

    	    return (root, query, cb) ->
    	            cb.like(cb.lower(root.get("adolescente").get("cidade")),
    	                    "%" + cidade.toLowerCase() + "%");
    }

    public static Specification<Internacao> cpf(String cpf) {
    	 if (cpf == null || cpf.isBlank()) return alwaysTrue();

    	    return (root, query, cb) ->
    	            cb.like(root.get("adolescente").get("cpf"),
    	                    "%" + cpf + "%");
    }

    public static Specification<Internacao> nascimentoEntre(LocalDate inicio, LocalDate fim) {
    	if (inicio == null || fim == null) return alwaysTrue();

        return (root, query, cb) ->
                cb.between(root.get("adolescente").get("dataNascimento"), inicio, fim);
    }

    public static Specification<Internacao> statusCadastro(Integer[] status) {
    	 if (status == null || status.length == 0) return alwaysTrue();

    	    return (root, query, cb) ->
    	            root.get("adolescente").get("status").in((Object[]) status);
    }
    
    private static Specification<Internacao> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }
}
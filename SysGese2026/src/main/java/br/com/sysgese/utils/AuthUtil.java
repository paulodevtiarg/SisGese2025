package br.com.sysgese.utils;

import java.util.Optional;
import java.util.Set;

import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.LotacaoRepository;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {

	public static boolean isMaster(HttpSession session) {
	    Boolean isMaster = (Boolean) session.getAttribute("isMaster");
	    return isMaster != null && isMaster;
	}
    
    /**
     * Retorna o ID da unidade do usuário logado, buscando via lotação ativa
     */

public static Long getUnidadeId(HttpSession session) {
    return (Long) session.getAttribute("unidadeId");
}
}

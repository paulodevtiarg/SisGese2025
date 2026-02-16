package br.com.sysgese.utils;

import java.util.Optional;

import br.com.sysgese.models.Lotacao;
import br.com.sysgese.models.Servidor;
import br.com.sysgese.repository.LotacaoRepository;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {

    public static boolean isMaster(HttpSession session) {
        Servidor s = (Servidor) session.getAttribute("usuarioLogado");
        return s != null && "MASTER".equals(s.getPerfil().getDescricao());
    }
    
    /**
     * Retorna o ID da unidade do usuário logado, buscando via lotação ativa
     */
    public static Long getUnidadeId(HttpSession session, LotacaoRepository lotacaoRepository) {
        Servidor s = (Servidor) session.getAttribute("usuarioLogado");
        if (s == null) {
            throw new RuntimeException("Usuário não está logado.");
        }

        Optional<Lotacao> lotacaoOpt = lotacaoRepository.findAtivaByServidorId(s.getId());
        if (lotacaoOpt.isEmpty()) {
            throw new RuntimeException("Usuário não possui lotação ativa.");
        }

        return lotacaoOpt.get().getUnidade().getId();
    }
}

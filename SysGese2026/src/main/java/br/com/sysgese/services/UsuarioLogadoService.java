package br.com.sysgese.services;

import org.springframework.stereotype.Service;

import br.com.sysgese.dtos.LotacaoDTO;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioLogadoService {

    private final HttpSession session;

    public UsuarioLogadoService(HttpSession session) {
        this.session = session;
    }

    public Long getUnidadeDoUsuarioLogado() {
        LotacaoDTO lotacao = (LotacaoDTO) session.getAttribute("lotacaoUsuarioLogado");
        if (lotacao != null) {
            return Long.valueOf(lotacao.getUnidadeId());
        }
        return null; // ou lançar exceção se obrigatório
    }
}

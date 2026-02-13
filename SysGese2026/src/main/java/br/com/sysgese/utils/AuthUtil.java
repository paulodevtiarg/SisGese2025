package br.com.sysgese.utils;

import br.com.sysgese.models.Servidor;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {

    public static boolean isMaster(HttpSession session) {
        Servidor s = (Servidor) session.getAttribute("usuarioLogado");
        return s != null && "MASTER".equals(s.getPerfil().getDescricao());
    }
}

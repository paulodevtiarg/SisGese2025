package br.com.sysgese.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.sysgese.models.Servidor;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("usuarioLogado")
    public Servidor getUsuarioLogado(HttpSession session) {
        return (Servidor) session.getAttribute("usuarioLogado");
    }
}